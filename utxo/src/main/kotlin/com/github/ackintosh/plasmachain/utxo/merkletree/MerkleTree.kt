package com.github.ackintosh.plasmachain.utxo.merkletree

import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class MerkleTree {
    data class Leaf(override val transactionHash: TransactionHash) :
        MerkleNode

    data class Node(override val transactionHash: TransactionHash, val left: MerkleNode, val right: MerkleNode) :
        MerkleNode

    companion object {
        fun build(transactionHashes: List<TransactionHash>) : Node {
            val leaves = padWithZeroHash(transactionHashes)
                .map { Leaf(it) }
            return buildTree(leaves)
        }

        private fun padWithZeroHash(transactionHashes: List<TransactionHash>) =
            when {
                transactionHashes.size == 1 -> listOf(transactionHashes.first(), TransactionHash.ZERO)
                isPowerOfTwo(transactionHashes.size) -> transactionHashes
                else -> {
                    var n = transactionHashes.size
                    val padded = ArrayList<TransactionHash>()
                    padded.addAll(transactionHashes)
                    while (!isPowerOfTwo(n)) {
                        padded.add(TransactionHash.ZERO)
                        n++
                    }
                    padded
                }
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
                            transactionHash = concatHash(
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
            TransactionHash(
                Hashing
                    .sha256()
                    .hashString(
                        "${left.transactionHash.value}${right.transactionHash.value}",
                        StandardCharsets.UTF_8
                    )
                    .toString()
            )
    }
}

interface MerkleNode {
    val transactionHash: TransactionHash
}
