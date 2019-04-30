package com.github.ackintosh.plasmachain.utxo.transaction

class GenerationInput(
    private val coinbaseData: CoinbaseData
) {
    fun transactionHash() = Hash.GENERATION
    fun outputIndex() = OutputIndex.GENERATION
}