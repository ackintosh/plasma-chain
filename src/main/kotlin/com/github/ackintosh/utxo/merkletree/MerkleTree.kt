package com.github.ackintosh.utxo.merkletree

import com.github.ackintosh.utxo.transaction.Hash

class MerkleTree {
    data class Leaf(override val hash: Hash) : MerkleNode

    data class Node(override val hash: Hash, val left: MerkleNode, val right: MerkleNode) : MerkleNode

    companion object {
        fun build(hashes: List<Hash>) : Node {
            // TODO: pad with zero-hash if number of the leaves is not a power of 2
            val leaves = hashes.map { Leaf(it) }
            return buildTree(leaves)
        }

        private fun buildTree(nodes: List<MerkleNode>) =
            if (nodes.size == 1) {
                nodes.first() as Node
            } else {
                val left = nodes[0]
                val right = nodes[1]
                val hash = "${left.hash.hash}${right.hash.hash}"
                Node(Hash(hash), left, right)
            }
    }
}

interface MerkleNode {
    val hash: Hash
}
