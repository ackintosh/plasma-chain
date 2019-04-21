package com.github.ackintosh.utxo

class BlockHeader(
    private val previousBlockHash: PreviousBlockHash
)

data class PreviousBlockHash(val hash: String)