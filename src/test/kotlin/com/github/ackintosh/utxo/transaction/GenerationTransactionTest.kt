package com.github.ackintosh.utxo.transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerationTransactionTest {
    @Test
    fun transactionHash() {
        assertEquals(
            Hash.GENERATION,
            GenerationTransaction().transactionHash()
        )
    }

    @Test
    fun outputIndex() {
        assertEquals(
            OutputIndex.GENERATION,
            GenerationTransaction().outputIndex()
        )
    }
}