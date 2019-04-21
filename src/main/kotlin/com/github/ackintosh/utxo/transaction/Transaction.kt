package com.github.ackintosh.utxo.transaction

class Transaction(
    private val inputs: List<Input>,
    private val outputs: List<Output>
) {
    fun inputCount() = inputs.count()
    fun outputCount() = outputs.count()
}
