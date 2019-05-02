package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.google.common.hash.Hashing
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.Security
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import java.util.*

class TransactionVerificationService {
    companion object {
        fun verifyTransactionScript(input: TransactionInput, utxo: Output) : Result {
            val stack = ArrayDeque<String>()

            val unlockingScript = input.unlockingScript()
            unlockingScript.split(' ').forEach { stack.push(it) }

            utxo.lockingScript().split(' ').forEach {
                when (it) {
                    "OP_DUP" -> stack.push(stack.first)
                    "OP_HASH160" -> {
                        val elem = stack.pop()
                        val s = Hashing.sha256().hashString(elem, StandardCharsets.UTF_8)

                        Security.addProvider(BouncyCastleProvider())
                        val rmd = MessageDigest.getInstance("RipeMD160", BouncyCastleProvider.PROVIDER_NAME)
                        val r = rmd.digest(s.asBytes())
                        stack.push(r.toHexString())
                    }
                    "OP_EQUALVERIFY" -> {
                        val elem1 = stack.pop()
                        val elem2 = stack.pop()
                        if (elem1 != elem2) return Result.Failure()
                    }
                    "OP_CHECKSIG" -> {
                        val publicKeyString = stack.pop()
                        val signatureString = stack.pop()

                        val signature = Base64.decode(signatureString)
                        val instance = Signature.getInstance("NONEwithECDSA")

                        val publicKeyByteArray = publicKeyString.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                        val publicKeySpec = X509EncodedKeySpec(publicKeyByteArray)
                        val keyfactory = KeyFactory.getInstance("EC")
                        val publicKey = keyfactory.generatePublic(publicKeySpec)

                        instance.initVerify(publicKey)
                        instance.update("${input.transactionHash().value}${input.outputIndex().toHexString()}".toByteArray())
                        if (instance.verify(signature)) {
                            stack.push("TRUE")
                        }
                    }
                    else -> stack.push(it)
                }
            }

            return if (stack.size == 1 && stack.pop() == "TRUE") {
                Result.Success()
            } else {
                Result.Failure()
            }
        }
    }

    sealed class Result {
        class Success : Result()
        class Failure : Result()
    }
}