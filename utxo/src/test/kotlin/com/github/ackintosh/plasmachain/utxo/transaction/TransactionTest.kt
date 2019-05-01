package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPublicKey

class TransactionTest {
    private val keyPair = Address.generateKeyPair()
    private val address = Address.from(keyPair)

    @Test
    fun inputCount() {
        val transaction = Transaction(
            inputs = listOf(
                Input(
                    transactionHash = Hash("xxx"),
                    outputIndex = OutputIndex(0u),
                    signature = "x",
                    publicKey = keyPair.public as ECPublicKey
                ),
                Input(
                    transactionHash = Hash("yyy"),
                    outputIndex = OutputIndex(0u),
                    signature = "x",
                    publicKey = keyPair.public as ECPublicKey
                )
            ),
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
            inputs = listOf(
                Input(
                    transactionHash = Hash("xxx"),
                    outputIndex = OutputIndex(0u),
                    signature = "x",
                    publicKey = keyPair.public as ECPublicKey
                ),
                Input(
                    transactionHash = Hash("yyy"),
                    outputIndex = OutputIndex(0u),
                    signature = "x",
                    publicKey = keyPair.public as ECPublicKey
                )
            ),
            outputs = listOf(
                Output(100, address),
                Output(200, address)
            )
        )

        assertEquals(
            Hash("b6103b98170f7587c62c5314ccc81d38a479fe469f419c6ce89e46f8d87188fa"),
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