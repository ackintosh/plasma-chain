package com.github.ackintosh.plasmachain.utxo.extensions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StringTest {
    @Test
    fun stripHexPrefix() {
        Assertions.assertEquals("1a2b", "0x1a2b".stripHexPrefix())
        Assertions.assertEquals("a1b2", "a1b2".stripHexPrefix())
    }
}