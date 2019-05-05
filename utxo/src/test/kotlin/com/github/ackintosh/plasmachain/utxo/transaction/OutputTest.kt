package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class OutputTest {
    val output = Output(
        BigInteger("10"),
        Address.from(Address.generateKeyPair())
    )

    @Test
    fun lockingScript() {
        val lockingScript = output.lockingScript()

        Assertions.assertEquals(85, lockingScript.length)
        Assertions.assertTrue(lockingScript.startsWith("OP_DUP OP_HASH160 "))
        Assertions.assertTrue(lockingScript.endsWith(" OP_EQUALVERIFY OP_CHECKSIG"))
    }

    @Test
    fun toHexString() {
        Assertions.assertEquals("000000000000000a", output.toHexString())
    }
}