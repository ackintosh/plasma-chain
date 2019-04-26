package com.github.ackintosh.utxo

import com.github.ackintosh.utxo.merkletree.MerkleTree

class BlockHeader(
    val previousBlockHash: PreviousBlockHash,
    val merkleRoot: MerkleTree.Node
)

data class PreviousBlockHash(val hash: String)