package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlockTest {
    @Test
    fun transactionCounter() {
        val transactions = listOf(
            Transaction(inputs = emptyList(), outputs = emptyList()),
            Transaction(inputs = emptyList(), outputs = emptyList())
        )

        val block = Block(
            header = Header(
                previousBlockHash = PreviousBlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals(2, block.transactionCounter())
    }

    @Test
    fun blockHash() {
        val transactions = listOf(
            Transaction(inputs = emptyList(), outputs = emptyList()),
            Transaction(inputs = emptyList(), outputs = emptyList())
        )

        val block = Block(
            header = Header(
                previousBlockHash = PreviousBlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals("90c5ddbc0a88ea749b48e0c07ff66381abbaddce2f076bb1055112348135bb1a", block.blockHash().value)
    }
}