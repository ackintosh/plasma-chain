package com.github.ackintosh.plasmachain.utxo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AddressTest {
    @Test
    fun address() {
        val address = Address.from(
            Address.generateKeyPair()
        )

        Assertions.assertEquals(40, address.rawString().length)
    }
}