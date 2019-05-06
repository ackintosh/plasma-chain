package com.github.ackintosh.plasmachain.utxo.merkletree

import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MerkleTreeTest {
    @Nested
    inner class `build()` {
        @Test
        fun singleHash() {
            val rootNode = MerkleTree.build(listOf(TransactionHash("xxx")))
            assertEquals(
                TransactionHash("29cc108c95c365c77fc086d68387762181c1ef7d378ebb670c598b007263a555"),
                rootNode.transactionHash
            )
        }

        @Test
        fun onlyTwoHashes() {
            val rootNode = MerkleTree.build(
                listOf(
                    TransactionHash("xxx"),
                    TransactionHash("yyy")
                )
            )

            assertEquals(
                TransactionHash("1073df16287683171f8e0cc7e265c7715e4ff73f503e5adffe258aa1f2dca5cf"),
                rootNode.transactionHash
            )
        }

        @Test
        fun moreThanTwo() {
            val rootNode = MerkleTree.build(
                listOf(
                    TransactionHash("xxx"),
                    TransactionHash("yyy"),
                    TransactionHash("aaa"),
                    TransactionHash("bbb")
                )
            )

            assertEquals(
                TransactionHash("e3b05c72d7a8d522fa0b1dbc1a4382342657c5cd1dd2948051ff516e7e30dfec"),
                rootNode.transactionHash
            )
        }

        @Test
        fun paddingWithZeroHash() {
            val rootNode = MerkleTree.build(
                listOf(
                    TransactionHash("xxx"),
                    TransactionHash("yyy"),
                    TransactionHash("aaa")
                )
            )

            assertEquals(
                TransactionHash("9519fa38c80d077015424640b82fbcab0cb81167f5aaac2e1ae85962acf9e534"),
                rootNode.transactionHash
            )
        }
    }
}