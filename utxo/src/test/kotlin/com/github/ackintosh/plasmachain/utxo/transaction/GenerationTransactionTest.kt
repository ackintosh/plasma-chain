package com.github.ackintosh.plasmachain.utxo.transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerationTransactionTest {
    @Test
    fun transactionHash() {
        assertEquals(
            Hash.GENERATION,
            GenerationTransaction(
                CoinbaseData(
                    "x"
                )
            ).transactionHash()
        )
    }

    @Test
    fun outputIndex() {
        assertEquals(
            OutputIndex.GENERATION,
            GenerationTransaction(
                CoinbaseData(
                    "x"
                )
            ).outputIndex()
        )
    }
}