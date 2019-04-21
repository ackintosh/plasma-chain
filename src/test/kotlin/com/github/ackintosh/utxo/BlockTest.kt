package com.github.ackintosh.utxo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlockTest {
    @Test
    fun transactionCounter() {
        val block = Block(
            header = BlockHeader(PreviousBlockHash("xxx")),
            transactions = listOf(
                Transaction(inputs = emptyList(), outputs = emptyList()),
                Transaction(inputs = emptyList(), outputs = emptyList())
            )
        )

        assertEquals(2, block.transactionCounter())
    }
}