package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlockTest {
    @Test
    fun transactionCounter() {
        val transactions = listOf(
            Transaction(input1 = emptyList(), outputs = emptyList()),
            Transaction(input1 = emptyList(), outputs = emptyList())
        )

        val block = Block(
            header = Header(
                previousBlockHash = BlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals(2, block.transactionCounter())
    }

    @Test
    fun blockHash() {
        val transactions = listOf(
            Transaction(input1 = emptyList(), outputs = emptyList()),
            Transaction(input1 = emptyList(), outputs = emptyList())
        )

        val block = Block(
            header = Header(
                previousBlockHash = BlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals("25f2632f62d243fdbd1835601642521ddccb556a90ac12faf330b0a2268d42d5", block.blockHash().value)
    }
}