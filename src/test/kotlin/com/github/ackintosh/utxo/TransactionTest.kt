package com.github.ackintosh.utxo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionTest {
    @Test
    fun inputCount() {
        val transaction = Transaction(
            inputs = listOf(
                TransactionInput(TransactionHash("xxx"), OutputIndex(0u)),
                TransactionInput(TransactionHash("yyy"), OutputIndex(1u))
            ),
            outputs = emptyList()
        )

        assertEquals(2, transaction.inputCount())
    }

    @Test
    fun outputCount() {
        val transaction = Transaction(
            inputs = emptyList(),
            outputs = listOf(TransactionOutput(100), TransactionOutput(200))
        )

        assertEquals(2, transaction.outputCount())
    }
}