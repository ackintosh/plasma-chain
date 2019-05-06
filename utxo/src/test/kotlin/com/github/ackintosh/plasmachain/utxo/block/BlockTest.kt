package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Input
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Signature
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.interfaces.ECPublicKey

class BlockTest {
    @Test
    fun blockHash() {
        val input = Input(
            transactionHash = TransactionHash.GENERATION,
            outputIndex = OutputIndex.GENERATION,
            signature = Signature("xxx"),
            publicKey = Address.generateKeyPair().public as ECPublicKey
        )
        val output = Output(
            amount = BigInteger("10"),
            address = Address.from(Address.generateKeyPair())
        )
        val transactions = listOf(
            Transaction(input1 = input, output1 = output),
            Transaction(input1 = input, output1 = output)
        )

        val block = Block(
            header = Header(
                previousBlockHash = BlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertTrue(block.blockHash().value.length == 64)
    }
}