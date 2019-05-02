package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class InputTest {
    private val keyPair = Address.generateKeyPair()

    @Test
    fun toHexString() {
        val transactionHash = Hash("xxx")
        val outputIndex = OutputIndex(10u)

        Assertions.assertEquals(
            "xxx0000000a",
            Input(
                transactionHash = transactionHash,
                outputIndex = outputIndex,
                signature = Signature.create(
                    keyPair.private as ECPrivateKey,
                    transactionHash,
                    outputIndex
                ),
                publicKey = keyPair.public as ECPublicKey
            ).toHexString()
        )
    }
}