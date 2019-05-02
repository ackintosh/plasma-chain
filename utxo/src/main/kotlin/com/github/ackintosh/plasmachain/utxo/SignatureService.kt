package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.transaction.Hash
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Signature
import org.bouncycastle.util.encoders.Base64
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class SignatureService {
    companion object {
        fun create(
            privateKey: ECPrivateKey,
            transactionHash: Hash,
            outputIndex: OutputIndex
        ): Signature {
            val d = "${transactionHash.value}${outputIndex.toHexString()}"
            val instance = java.security.Signature.getInstance("NONEwithECDSA")
            instance.initSign(privateKey)
            instance.update(d.toByteArray())

            return Signature(
                Base64.toBase64String(instance.sign())
            )
        }

        fun verify(publicKey: ECPublicKey) {
            TODO("not implemented")
        }
    }
}
