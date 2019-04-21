package com.github.ackintosh.utxo

class Transaction(
    private val inputs: List<TransactionInput>,
    private val outputs: List<TransactionOutput>
) {
    fun inputCount() = inputs.count()
    fun outputCount() = outputs.count()
}
