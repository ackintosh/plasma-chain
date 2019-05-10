package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureCreationService
import com.github.ackintosh.plasmachain.utxo.block.BlockNumber
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import com.github.ackintosh.plasmachain.utxo.transaction.Input
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

@kotlin.ExperimentalUnsignedTypes
class NodeTest {
    @Test
    fun sendCoinsToBob() {
        val node = Node()
        val genesisTransaction = node.getGenesisBlock().transactions.first()

        val input = Input(
            transactionHash = genesisTransaction.transactionHash(),
            outputIndex = OutputIndex(0u),
            signature = SignatureCreationService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = genesisTransaction.transactionHash(),
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = BigInteger("10"),
            address = bob
        )

        val transaction = Transaction(
            input1 = input,
            output1 = output
        )

        Assertions.assertTrue(node.addTransaction(transaction))
    }

    @Test
    fun incorrectTransactionInput() {
        val node = Node()
        val incorrectTransactionHash = TransactionHash(ByteArray(32) { 1.toByte() }.toHexString())

        val input = Input(
            transactionHash = incorrectTransactionHash,
            outputIndex = OutputIndex(0u),
            signature = SignatureCreationService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = incorrectTransactionHash,
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = BigInteger("10"),
            address = bob
        )

        val transaction = Transaction(
            input1 = input,
            output1 = output
        )

        Assertions.assertFalse(node.addTransaction(transaction))
    }

    @Test
    fun createNewBlock() {
        val node = Node()
        val genesisTransaction = node.getGenesisBlock().transactions.first()

        val input = Input(
            transactionHash = genesisTransaction.transactionHash(),
            outputIndex = OutputIndex(0u),
            signature = SignatureCreationService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = genesisTransaction.transactionHash(),
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = BigInteger("10"),
            address = bob
        )

        val transaction = Transaction(
            input1 = input,
            output1 = output
        )

        Assertions.assertTrue(node.addTransaction(transaction))
        Assertions.assertTrue(node.createNewBlock())
        Assertions.assertEquals(BlockNumber(1000u), node.getLatestBlock().number)
    }

    @Test
    fun handleDepositedEvent() {
        val node = Node()
        val address = Address.from(Address.generateKeyPair())
        val amount = BigInteger("1000")
        val depositBlockNumber = BlockNumber(2u)
        node.handleDepositedEvent(address, amount, depositBlockNumber)

        val block = node.getLatestBlock()

        Assertions.assertEquals(1, block.transactions.size)
        Assertions.assertNull(block.transactions.first().output2)
        Assertions.assertEquals(address, block.transactions.first().output1.address)
        Assertions.assertEquals(amount, block.transactions.first().output1.amount)
    }
}