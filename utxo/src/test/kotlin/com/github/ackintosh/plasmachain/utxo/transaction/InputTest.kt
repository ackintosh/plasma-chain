package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPublicKey

class InputTest {
    private val keyPair = Address.generateKeyPair()

    @Test
    fun toHexString() {
        Assertions.assertEquals(
            "xxx0000000a",
            Input(
                transactionHash = Hash("xxx"),
                outputIndex = OutputIndex(10u),
                signature = "xxx",
                publicKey = keyPair.public as ECPublicKey
            ).toHexString()
        )
    }
}