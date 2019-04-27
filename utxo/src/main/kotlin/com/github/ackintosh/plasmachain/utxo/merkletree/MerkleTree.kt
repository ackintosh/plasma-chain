package com.github.ackintosh.plasmachain.utxo.merkletree

import com.github.ackintosh.plasmachain.utxo.transaction.Hash
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class MerkleTree {
    data class Leaf(override val hash: Hash) :
        MerkleNode

    data class Node(override val hash: Hash, val left: MerkleNode, val right: MerkleNode) :
        MerkleNode

    companion object {
        fun build(hashes: List<Hash>) : Node {
            val leaves = padWithZeroHash(hashes)
                .map { Leaf(it) }
            return buildTree(leaves)
        }

        private fun padWithZeroHash(hashes: List<Hash>) =
            if (isPowerOfTwo(hashes.size)) {
                hashes
            } else {
                var n = hashes.size
                val padded = ArrayList<Hash>()
                padded.addAll(hashes)
                while (!isPowerOfTwo(n)) {
                    padded.add(Hash.ZERO)
                    n++
                }
                padded
            }

        // http://www.exploringbinary.com/ten-ways-to-check-if-an-integer-is-a-power-of-two-in-c/
        // > 9. Decrement and Compare
        private fun isPowerOfTwo(n: Int) =
            (n != 0) and (n and (n - 1) == 0)

        private fun buildTree(nodes: List<MerkleNode>) : Node =
            if (nodes.size == 1) {
                nodes.first() as Node
            } else {
                val n = nodes.size
                val combined: MutableList<MerkleNode> = ArrayList()
                for (i in 0..n - 2 step 2) {
                    val left = nodes[i]
                    val right = nodes[i + 1]
                    combined.add(
                        Node(
                            hash = concatHash(
                                left,
                                right
                            ),
                            left = left,
                            right = right
                        )
                    )
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
