package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionVerificationServiceTest {
    @Test
    fun verifyTransactionScript() {
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

        val result = TransactionVerificationService.verifyTransactionScript(
            input = input,
            utxo = output
        )

        Assertions.assertTrue(result is TransactionVerificationService.Result.Success)
    }

    @Test
    fun verifyTransactionScriptReturnsFailureResult() {
        val keyPair = Address.generateKeyPair()
        val address = Address.from(keyPair)

        val output = Output(
            amount = 100,
            address = address
        )

        val transactionHash = Hash("xxx")
        val outputIndex = OutputIndex(10u)
        val otherKeyPair = Address.generateKeyPair()
        val input = Input(
            transactionHash = transactionHash,
            outputIndex = outputIndex,
            signature = SignatureService.create(
                otherKeyPair.private as ECPrivateKey,
                transactionHash,
                outputIndex
            ),
            publicKey = otherKeyPair.public as ECPublicKey
        )

        val result = TransactionVerificationService.verifyTransactionScript(
            input = input,
            utxo = output
        )

        Assertions.assertTrue(result is TransactionVerificationService.Result.Failure)
    }
}