package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import java.security.interfaces.ECPublicKey

sealed class TransactionInput : TransactionInputInterface

interface TransactionInputInterface {
    fun transactionHash() : TransactionHash
    fun outputIndex() : OutputIndex
    fun toHexString() : String
    fun unlockingScript() : String
}

class Input(
    private val transactionHash: TransactionHash,
    private val outputIndex: OutputIndex,
    private val signature: Signature, // TODO: investigate how create the signature
    private val publicKey: ECPublicKey
) : TransactionInput() {
    override fun transactionHash() = transactionHash

    override fun outputIndex() = outputIndex

    override fun toHexString() = "${transactionHash.value}${outputIndex.toHexString()}"

    override fun unlockingScript() = "${signature.value} ${publicKey.encoded.toHexString()}"
}

class GenerationInput(
    private val coinbaseData: CoinbaseData
) : TransactionInput() {
    override fun transactionHash() = TransactionHash.GENERATION

    override fun outputIndex()= OutputIndex.GENERATION

    override fun toHexString() = "${TransactionHash.GENERATION}${OutputIndex.GENERATION.toHexString()}"

    override fun unlockingScript(): String {
        TODO("not implemented")
    }
}
