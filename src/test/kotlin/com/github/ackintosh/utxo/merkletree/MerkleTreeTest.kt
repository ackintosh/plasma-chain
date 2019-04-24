package com.github.ackintosh.utxo.merkletree

import com.github.ackintosh.utxo.transaction.Hash
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MerkleTreeTest {
    @Test
    fun build() {
        val rootNode = MerkleTree.build(listOf(
            Hash("xxx"), Hash("yyy")
        ))

        Assertions.assertEquals(Hash("xxxyyy"), rootNode.hash)
    }
}