package com.github.ackintosh.utxo.transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OutputTest {
    @Test
    fun toHexString() {
        assertEquals("000000000000000a", Output(10).toHexString())
    }
}