package com.github.ackintosh.utxo.merkletree

import com.github.ackintosh.utxo.transaction.Hash
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class MerkleTree {
    data class Leaf(override val hash: Hash) : MerkleNode

    data class Node(override val hash: Hash, val left: MerkleNode, val right: MerkleNode) : MerkleNode

    companion object {
        fun build(hashes: List<Hash>) : Node {
            // TODO: pad with zero-hash if number of the leaves is not a power of 2
            val leaves = hashes.map { Leaf(it) }
            return buildTree(leaves)
        }

        private fun buildTree(nodes: List<MerkleNode>) : Node =
            if (nodes.size == 1) {
                nodes.first() as Node
            } else {
                val n = nodes.size
                val combined: MutableList<MerkleNode> = ArrayList()
                for (i in 0..n - 2 step 2) {
                    val left = nodes[i]
                    val right = nodes[i + 1]
                    combined.add(Node(
                        hash = concatHash(left, right),
                        left = left,
                        right = right
                    ))
                }
                buildTree(combined)
            }

        private fun concatHash(left: MerkleNode, right: MerkleNode) =
            Hash(
                Hashing
                    .sha256()
                    .hashString(
                        "${left.hash.hash}${right.hash.hash}",
                        StandardCharsets.UTF_8
                    )
                    .toString()
            )
    }
}

interface MerkleNode {
    val hash: Hash
}
