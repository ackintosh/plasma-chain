package com.github.ackintosh.utxo.transaction

data class TransactionInput(
    val transactionHash: TransactionHash,
    val outputIndex: OutputIndex
)