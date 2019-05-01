package com.github.ackintosh.plasmachain.utxo.transaction

sealed class TransactionInput : TransactionInterface

interface TransactionInterface {
    fun transactionHash() : Hash
    fun outputIndex() : OutputIndex
    fun toHexString() : String
}

class Input(
    private val transactionHash: Hash,
    private val outputIndex: OutputIndex
) : TransactionInput() {
    override fun transactionHash() = transactionHash

    override fun outputIndex() = outputIndex

    override fun toHexString() = "${transactionHash.value}${outputIndex.toHexString()}"
}

class GenerationInput(
    private val coinbaseData: CoinbaseData
) : TransactionInput() {
    override fun transactionHash() = Hash.GENERATION

    override fun outputIndex()= OutputIndex.GENERATION

    override fun toHexString() = "${Hash.GENERATION}${OutputIndex.GENERATION.toHexString()}"
}
