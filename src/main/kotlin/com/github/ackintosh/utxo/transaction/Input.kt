package com.github.ackintosh.utxo.transaction

data class Input(
    val transactionHash: Hash,
    val outputIndex: OutputIndex
)