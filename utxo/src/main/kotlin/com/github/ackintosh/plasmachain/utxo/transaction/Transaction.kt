package com.github.ackintosh.plasmachain.utxo.transaction

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Transaction(
    private val inputs: List<Input>,
    private val outputs: List<Output>
) {
    fun inputCount() = inputs.count()
    fun outputCount() = outputs.count()

    fun transactionHash() : Hash {
        val inputs = inputs.map { it.toHexString() }.joinToString("")
        val outputs = outputs.map { it.toHexString() }.joinToString("")

        val sha256Encoded = Hashing
                .sha256()
                .hashString("$inputs$outputs", StandardCharsets.UTF_8)

        return Hash(sha256Encoded
            .asBytes()
            .reversed()
            .map { String.format("%02X", it) }
            .joinToString("")
            .toLowerCase()
        )
    }
}
