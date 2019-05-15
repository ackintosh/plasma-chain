package com.github.ackintosh.plasmamvp.utxo.transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerationInputTest {
    @Test
    fun transactionHash() {
        assertEquals(
            TransactionHash.GENERATION,
            GenerationInput(
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
            GenerationInput(
                CoinbaseData(
                    "x"
                )
            ).outputIndex()
        )
    }
}