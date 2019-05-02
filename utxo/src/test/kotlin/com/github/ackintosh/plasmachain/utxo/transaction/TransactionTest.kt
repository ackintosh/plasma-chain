package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionTest {
    private val keyPair = Address.generateKeyPair()
    private val address = Address.from(keyPair)

    private val inputX = {
        val transactionHash = Hash("xxx")
        val outputIndex = OutputIndex(0u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = Signature.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    private val inputY = {
        val transactionHash = Hash("yyy")
        val outputIndex = OutputIndex(1u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = Signature.create(
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
                Output(100, address),
                Output(200, address)
            )
        )

        assertEquals(2, transaction.outputCount())
    }

    @Test
    fun transactionHash() {
        val transaction = Transaction(
            inputs = listOf(inputX, inputY),
            outputs = listOf(
                Output(100, address),
                Output(200, address)
            )
        )

        assertEquals(
            Hash("acb8ecc39eef636e69e530cfae5e7ccbec47f8a075007748d838e5cc7a3f95c6"),
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