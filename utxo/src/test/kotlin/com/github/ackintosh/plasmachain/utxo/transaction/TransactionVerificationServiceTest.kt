package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionVerificationServiceTest {
    @Test
    fun verifyInput() {
        val keyPair = Address.generateKeyPair()
        val address = Address.from(keyPair)

        val output = Output(
            amount = 100,
            address = address
        )

        val transactionHash = Hash("xxx")
        val outputIndex = OutputIndex(10u)
        val input = Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureService.create(
                keyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = keyPair.public as ECPublicKey
        )

        val result = TransactionVerificationService.verifyInput(
            input = input,
            utxo = output
        )

        Assertions.assertTrue(result is TransactionVerificationService.Result.Success)
    }
}