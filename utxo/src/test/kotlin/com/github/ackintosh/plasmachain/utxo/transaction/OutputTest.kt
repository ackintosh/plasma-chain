package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OutputTest {
    @Test
    fun toHexString() {
        val address = Address.from(Address.generateKeyPair())
        assertEquals("000000000000000a", Output(10, address).toHexString())
    }
}