package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureCreationService
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionTest {
    private val keyPair = Address.generateKeyPair()
    private val address = Address.from(keyPair)
    private val hashX = TransactionHash(ByteArray(32) { 1.toByte() }.toHexString())
    private val hashY = TransactionHash(ByteArray(32) { 2.toByte() }.toHexString())

    private val inputX = {
        val transactionHash = hashX
        val outputIndex = OutputIndex(0u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    private val inputY = {
        val transactionHash = hashY
        val outputIndex = OutputIndex(1u)
        Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureCreationService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )
    }.invoke()

    @Test
    fun transactionHash() {
        val transaction = Transaction(
            input1 = inputX,
            input2 = inputY,
            outputs = listOf(
                Output(BigInteger("100"), address),
                Output(BigInteger("200"), address)
            )
        )

        assertEquals(
            TransactionHash("cdcc9f0f301af8669194aa811366f375cb294995192a44c80d08771cb518cc6a"),
            transaction.transactionHash()
        )
    }
}