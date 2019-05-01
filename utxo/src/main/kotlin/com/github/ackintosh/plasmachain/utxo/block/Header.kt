package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree

class Header(
    val previousBlockHash: Hash,
    val merkleRoot: MerkleTree.Node
)
