package com.github.ackintosh.plasmachain.utxo.transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionTest {
    @Test
    fun inputCount() {
        val transaction = Transaction(
            inputs = listOf(
                Input(
                    Hash(
                        "xxx"
                    ), OutputIndex(0u)
                ),
                Input(
                    Hash(
                        "yyy"
                    ), OutputIndex(1u)
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
                Output(100),
                Output(200)
            )
        )

        assertEquals(2, transaction.outputCount())
    }

    @Test
    fun transactionHash() {
        val transaction = Transaction(
            inputs = listOf(
                Input(
                    Hash(
                        "xxx"
                    ), OutputIndex(0u)
                ),
                Input(
                    Hash(
                        "yyy"
                    ), OutputIndex(1u)
                )
            ),
            outputs = listOf(
                Output(100),
                Output(200)
            )
        )

        // a hash of "c6953f7acce538d848770075a0f847eccb7c5eaecf30e5696e63ef9ec3ecb8ac" with little-endian order.
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