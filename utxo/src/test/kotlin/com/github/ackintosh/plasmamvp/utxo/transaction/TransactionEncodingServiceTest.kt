package com.github.ackintosh.plasmamvp.utxo.transaction

import com.github.ackintosh.plasmamvp.utxo.Address
import com.github.ackintosh.plasmamvp.utxo.SignatureCreationService
import com.github.ackintosh.plasmamvp.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

@kotlin.ExperimentalUnsignedTypes
class TransactionEncodingServiceTest {
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
    fun encode() {
        val transaction = Transaction(
            input1 = inputX,
            input2 = inputY,
            output1 = Output(BigInteger("100"), address),
            output2 = Output(BigInteger("200"), address)
        )

        // TODO: improve the assertion
        Assertions.assertTrue(
            TransactionEncodingService.encode(transaction).toHexString().startsWith("F8DFB840303130313031303130313031303130")
        )
    }
}