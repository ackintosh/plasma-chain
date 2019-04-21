package com.github.ackintosh.utxo.transaction

class GenerationTransaction(
    private val coinbaseData: CoinbaseData
) {
    fun transactionHash() = Hash.GENERATION
    fun outputIndex() = OutputIndex.GENERATION
}