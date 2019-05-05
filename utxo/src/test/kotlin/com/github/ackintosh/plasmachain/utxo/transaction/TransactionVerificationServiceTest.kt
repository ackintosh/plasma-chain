package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.SignatureService
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class TransactionVerificationServiceTest {
    private val hash = Hash(ByteArray(32) { 1.toByte() }.toHexString())

    @Nested
    inner class `verify()` {
        val keyPair = Address.generateKeyPair()
        val address = Address.from(keyPair)

        @Test
        fun generationTransaction() {
            val chain = Chain(mutableListOf())
            val generationTransaction = Transaction(
                inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
                outputs = listOf(Output(BigInteger("100"), address))
            )

            Assertions.assertTrue(
                TransactionVerificationService.verify(chain, generationTransaction)
                        is TransactionVerificationService.Result.Success
            )
        }
    }

    @Nested
    inner class `verifyTransactionScript()` {
        @Nested
        inner class `if scripts are valid` {
            @Test
            fun `return success result`() {
                val keyPair = Address.generateKeyPair()
                val address = Address.from(keyPair)

                val output = Output(
                    amount = BigInteger("100"),
                    address = address
                )

                val transactionHash = hash
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
        }

        @Nested
        inner class `if signature is not match` {
            @Test
            fun `return failure result`() {
                val keyPair = Address.generateKeyPair()
                val address = Address.from(keyPair)

                val output = Output(
                    amount = BigInteger("100"),
                    address = address
                )

                val transactionHash = hash
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
    }
}