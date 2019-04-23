package com.github.ackintosh.utxo.transaction

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InputTest {
    @Test
    fun toHexString() {
        Assertions.assertEquals(
            "xxx0000000a",
            Input(Hash("xxx"), OutputIndex(10u)).toHexString()
        )
    }
}