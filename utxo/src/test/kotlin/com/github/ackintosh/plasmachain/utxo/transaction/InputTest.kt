package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureCreationService
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class InputTest {
    private val keyPair = Address.generateKeyPair()
    private val hash = Hash(ByteArray(32) { 1.toByte() }.toHexString())

    @Test
    fun toHexString() {
        val transactionHash = hash
        val outputIndex = OutputIndex(10u)

        Assertions.assertEquals(
            "01010101010101010101010101010101010101010101010101010101010101010000000a",
            Input(
                transactionHash = transactionHash,
                outputIndex = outputIndex,
                signature = SignatureCreationService.create(
                    keyPair.private as ECPrivateKey,
                    transactionHash,
                    outputIndex
                ),
                publicKey = keyPair.public as ECPublicKey
            ).toHexString()
        )
    }

    @Test
    fun unlockingScript() {
        val transactionHash = hash
        val outputIndex = OutputIndex(10u)
        val input = Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )

        Assertions.assertTrue(
            input.unlockingScript().matches("\\A.+\\s[0-9A-Z]+\\Z".toRegex())
        )
    }
}