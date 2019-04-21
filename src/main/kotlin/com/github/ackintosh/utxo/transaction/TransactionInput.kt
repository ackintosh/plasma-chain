package com.github.ackintosh.utxo.transaction

data class TransactionInput(
    val transactionHash: Hash,
    val outputIndex: OutputIndex
)