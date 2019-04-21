package com.github.ackintosh.utxo

data class TransactionInput(
    val transactionHash: TransactionHash,
    val outputIndex: OutputIndex
)