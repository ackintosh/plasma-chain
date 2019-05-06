package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.kethereum.keccakshortcut.keccak

class Transaction(
    val input1: TransactionInput,
    val input2: TransactionInput? = null,
    val output1:Output,
    val output2: Output? = null
) {
    @kotlin.ExperimentalUnsignedTypes
    fun transactionHash() = TransactionHash(
        TransactionEncodingService.encode(this).keccak().toHexString().toLowerCase()
    )

    @kotlin.ExperimentalUnsignedTypes
    fun findOutput(outputIndex: OutputIndex) = when (outputIndex.index.toInt()) {
        0 -> output1
        1 -> output2
        else -> null
    }
}
