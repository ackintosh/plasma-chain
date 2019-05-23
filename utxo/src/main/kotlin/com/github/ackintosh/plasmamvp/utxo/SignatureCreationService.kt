package com.github.ackintosh.plasmamvp.utxo

import com.github.ackintosh.plasmamvp.utxo.extensions.hexStringToByteArray
import com.github.ackintosh.plasmamvp.utxo.transaction.TransactionHash
import com.github.ackintosh.plasmamvp.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmamvp.utxo.transaction.Signature
import org.bouncycastle.util.encoders.Base64
import java.security.interfaces.ECPrivateKey

class SignatureCreationService {
    companion object {
        fun create(
            privateKey: ECPrivateKey,
            transactionHash: TransactionHash,
            outputIndex: OutputIndex
        ): Signature {
            val d = "${transactionHash.value}${outputIndex.toHexString()}"
            val instance = java.security.Signature.getInstance("NONEwithECDSA")
            instance.initSign(privateKey)
            instance.update(d.hexStringToByteArray())

            return Signature(
                Base64.toBase64String(instance.sign())
            )
        }
    }
}
