package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree

class BlockHeader(
    val previousBlockHash: PreviousBlockHash,
    val merkleRoot: MerkleTree.Node
)

data class PreviousBlockHash(val hash: String) {
    companion object {
        fun zero() = PreviousBlockHash(
            ByteArray(32) { _ -> 0 }.map { String.format("%02X", it) }.joinToString("")
        )
    }
}