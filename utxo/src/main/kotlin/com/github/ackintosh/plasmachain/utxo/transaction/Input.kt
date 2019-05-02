package com.github.ackintosh.plasmachain.utxo.transaction

import java.security.interfaces.ECPublicKey

sealed class TransactionInput : TransactionInterface

interface TransactionInterface {
    fun transactionHash() : Hash
    fun outputIndex() : OutputIndex
    fun toHexString() : String
    fun unlockingScript() : String
}

class Input(
    private val transactionHash: Hash,
    private val outputIndex: OutputIndex,
    private val signature: Signature, // TODO: investigate how create the signature
    private val publicKey: ECPublicKey
) : TransactionInput() {
    override fun transactionHash() = transactionHash

    override fun outputIndex() = outputIndex

    override fun toHexString() = "${transactionHash.value}${outputIndex.toHexString()}"

    override fun unlockingScript() = "${signature.value}${PublicKey.toString(publicKey)}"
}

class GenerationInput(
    private val coinbaseData: CoinbaseData
) : TransactionInput() {
    override fun transactionHash() = Hash.GENERATION

    override fun outputIndex()= OutputIndex.GENERATION

    override fun toHexString() = "${Hash.GENERATION}${OutputIndex.GENERATION.toHexString()}"

    override fun unlockingScript(): String {
        TODO("not implemented")
    }
}
