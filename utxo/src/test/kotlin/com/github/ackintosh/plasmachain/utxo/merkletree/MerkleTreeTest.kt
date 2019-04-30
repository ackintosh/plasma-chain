package com.github.ackintosh.plasmachain.utxo.merkletree

import com.github.ackintosh.plasmachain.utxo.transaction.Hash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MerkleTreeTest {
    @Nested
    inner class `build()` {
        @Test
        fun singleHash() {
            val rootNode = MerkleTree.build(listOf(Hash("xxx")))
            assertEquals(
                Hash("29cc108c95c365c77fc086d68387762181c1ef7d378ebb670c598b007263a555"),
                rootNode.hash
            )
        }

        @Test
        fun onlyTwoHashes() {
            val rootNode = MerkleTree.build(
                listOf(
                    Hash("xxx"),
                    Hash("yyy")
                )
            )

            assertEquals(
                Hash("1073df16287683171f8e0cc7e265c7715e4ff73f503e5adffe258aa1f2dca5cf"),
                rootNode.hash
            )
        }

        @Test
        fun moreThanTwo() {
            val rootNode = MerkleTree.build(
                listOf(
                    Hash("xxx"),
                    Hash("yyy"),
                    Hash("aaa"),
                    Hash("bbb")
                )
            )

            assertEquals(
                Hash("e3b05c72d7a8d522fa0b1dbc1a4382342657c5cd1dd2948051ff516e7e30dfec"),
                rootNode.hash
            )
        }

        @Test
        fun paddingWithZeroHash() {
            val rootNode = MerkleTree.build(
                listOf(
                    Hash("xxx"),
                    Hash("yyy"),
                    Hash("aaa")
                )
            )

            assertEquals(
                Hash("9519fa38c80d077015424640b82fbcab0cb81167f5aaac2e1ae85962acf9e534"),
                rootNode.hash
            )
        }
    }
}