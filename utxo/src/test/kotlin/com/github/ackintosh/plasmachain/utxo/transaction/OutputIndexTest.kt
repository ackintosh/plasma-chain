package com.github.ackintosh.plasmachain.utxo.transaction

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OutputIndexTest {
    @Test
    fun toHexString() {
        Assertions.assertEquals("0000000a", OutputIndex(10u).toHexString())
    }
}