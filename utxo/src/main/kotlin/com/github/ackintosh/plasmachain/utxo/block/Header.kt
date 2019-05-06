package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree

class Header(
    val previousBlockHash: BlockHash,
    val merkleRoot: MerkleTree.Node
)
