package com.github.ackintosh.utxo

import com.github.ackintosh.utxo.merkletree.MerkleTree

class BlockHeader(
    private val previousBlockHash: PreviousBlockHash,
    private val merkleRoot: MerkleTree.Node
)

data class PreviousBlockHash(val hash: String)