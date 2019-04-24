package com.github.ackintosh.utxo.merkletree

import com.github.ackintosh.utxo.transaction.Hash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MerkleTreeTest {
    @Test
    fun build() {
        val rootNode = MerkleTree.build(listOf(
            Hash("xxx"), Hash("yyy")
        ))

        assertEquals(
            Hash("1073df16287683171f8e0cc7e265c7715e4ff73f503e5adffe258aa1f2dca5cf"),
            rootNode.hash
        )
    }
}