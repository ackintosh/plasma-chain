package com.github.ackintosh.utxo

import com.github.ackintosh.utxo.merkletree.MerkleTree
import com.github.ackintosh.utxo.transaction.Transaction
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
            header = BlockHeader(
                previousBlockHash = PreviousBlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals(2, block.transactionCounter())
    }
}