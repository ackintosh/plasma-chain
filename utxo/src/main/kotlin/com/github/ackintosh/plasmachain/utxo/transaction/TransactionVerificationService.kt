package com.github.ackintosh.plasmachain.utxo.transaction

import com.google.common.hash.Hashing
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.Security
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
                        stack.push(
                            r.map { byte -> String.format("%02X", byte) }
                                .joinToString("")
                        )
                    }
                    "OP_EQUALVERIFY" -> {
                        val elem1 = stack.pop()
                        val elem2 = stack.pop()
                        if (elem1 != elem2) return Result.Failure()
                    }
                    "OP_CHECKSIG" -> {
                        val publicKeyString = stack.pop()
                        val signatureString = stack.pop()
                        // TODO: check if the elements are valid
                    }
                    else -> stack.push(it)
                }
            }

            return Result.Success()
        }
    }

    sealed class Result {
        class Success : Result()
        class Failure : Result()
    }
}