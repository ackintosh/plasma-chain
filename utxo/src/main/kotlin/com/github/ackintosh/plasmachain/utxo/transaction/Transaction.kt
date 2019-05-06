package com.github.ackintosh.plasmachain.utxo.transaction

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Transaction(
    val input1: TransactionInput,
    val input2: TransactionInput? = null,
    val outputs: List<Output>
) {
    fun transactionHash() : TransactionHash {
        // TODO: input2
        val inputs = input1.toHexString()
        val outputs = outputs.map { it.toHexString() }.joinToString("")

        val sha256Encoded = Hashing
                .sha256()
                .hashString("$inputs$outputs", StandardCharsets.UTF_8)

        return TransactionHash(sha256Encoded
            .asBytes()
            .reversed()
            .map { String.format("%02X", it) }
            .joinToString("")
            .toLowerCase()
        )
    }

    @kotlin.ExperimentalUnsignedTypes
    fun findOutput(outputIndex: OutputIndex) = outputs[outputIndex.index.toInt()]
}
