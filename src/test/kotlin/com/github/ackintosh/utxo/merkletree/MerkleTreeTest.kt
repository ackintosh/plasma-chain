package com.github.ackintosh.utxo.merkletree

import com.github.ackintosh.utxo.transaction.Hash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MerkleTreeTest {
    @Nested
    inner class `build()` {
        @Test
        fun onlyTwoHashes() {
            val rootNode = MerkleTree.build(listOf(
                Hash("xxx"), Hash("yyy")
            ))

            assertEquals(
                Hash("1073df16287683171f8e0cc7e265c7715e4ff73f503e5adffe258aa1f2dca5cf"),
                rootNode.hash
            )
        }

        @Test
        fun moreThanTwo() {
            val rootNode = MerkleTree.build(listOf(
                Hash("xxx"), Hash("yyy"),
                Hash("aaa"), Hash("bbb")
            ))

            assertEquals(
                Hash("e3b05c72d7a8d522fa0b1dbc1a4382342657c5cd1dd2948051ff516e7e30dfec"),
                rootNode.hash
            )
        }
    }
}