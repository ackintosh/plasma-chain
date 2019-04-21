package com.github.ackintosh.utxo.transaction

class GenerationTransaction {
    fun transactionHash() = Hash.GENERATION
    fun outputIndex() = OutputIndex.GENERATION
}