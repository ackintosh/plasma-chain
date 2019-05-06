package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureCreationService
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionTest {
    private val keyPair = Address.generateKeyPair()
    private val address = Address.from(keyPair)
    private val hashX = TransactionHash(ByteArray(32) { 1.toByte() }.toHexString())
    private val hashY = TransactionHash(ByteArray(32) { 2.toByte() }.toHexString())

    private val inputX = {
        val transactionHash = hashX
        val outputIndex = OutputIndex(0u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    private val inputY = {
        val transactionHash = hashY
        val outputIndex = OutputIndex(1u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    @Test
    fun inputCount() {
        val transaction = Transaction(
            inputs = listOf(inputX, inputY),
            outputs = emptyList()
        )

        assertEquals(2, transaction.inputCount())
    }

    @Test
    fun outputCount() {
        val transaction = Transaction(
            inputs = emptyList(),
            outputs = listOf(
                Output(BigInteger("100"), address),
                Output(BigInteger("200"), address)
            )
        )

        assertEquals(2, transaction.outputCount())
    }

    @Test
    fun transactionHash() {
        val transaction = Transaction(
            inputs = listOf(inputX, inputY),
            outputs = listOf(
                Output(BigInteger("100"), address),
                Output(BigInteger("200"), address)
            )
        )

        assertEquals(
            TransactionHash("caa6ed633715c8fd497cbea71060a0c8708b2820a226dcefb0af3e6e729608b2"),
            transaction.transactionHash()
        )
    }

    @Test
    fun generationTransaction() {
        val transaction = Transaction(
            inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
            outputs = emptyList()
        )
    }
}