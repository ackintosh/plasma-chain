package com.github.ackintosh.plasmamvp.utxo.transaction

import com.github.ackintosh.plasmamvp.utxo.Address
import com.github.ackintosh.plasmamvp.utxo.SignatureCreationService
import com.github.ackintosh.plasmamvp.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

@kotlin.ExperimentalUnsignedTypes
class TransactionTest {
    private val keyPair = Address.generateKeyPair()
    private val address = Address.from(keyPair)
    private val hashX = TransactionHash(ByteArray(32) { 1.toByte() }.toHexString())
    private val hashY = TransactionHash(ByteArray(32) { 2.toByte() }.toHexString())

    private val inputX = {
        val outputIndex = OutputIndex(0u)
        Input(
            transactionHash = hashX,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                hashX,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    private val inputY = {
        val outputIndex = OutputIndex(1u)
        Input(
            transactionHash = hashY,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                hashY,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    @Test
    fun transactionHash() {
        val transaction = Transaction(
            input1 = inputX,
            input2 = inputY,
            output1 = Output(BigInteger("100"), address),
            output2 = Output(BigInteger("200"), address)
        )

        assertTrue(
            transaction.transactionHash().value.length == 64
        )
    }
}