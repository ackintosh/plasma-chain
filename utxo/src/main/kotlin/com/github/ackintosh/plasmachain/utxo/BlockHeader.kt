package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree

class BlockHeader(
    val previousBlockHash: PreviousBlockHash,
    val merkleRoot: MerkleTree.Node
)

data class PreviousBlockHash(val hash: String)