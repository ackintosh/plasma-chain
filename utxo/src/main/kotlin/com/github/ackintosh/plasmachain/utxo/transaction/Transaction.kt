package com.github.ackintosh.plasmachain.utxo.transaction

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Transaction(
    val input1: List<TransactionInput>,
    val outputs: List<Output>
) {
    fun outputCount() = outputs.count()

    fun transactionHash() : TransactionHash {
        val inputs = input1.map { it.toHexString() }.joinToString("")
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

    fun findOutput(outputIndex: OutputIndex) = outputs[outputIndex.index.toInt()]
}
