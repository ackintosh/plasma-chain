package com.github.ackintosh.utxo.transaction

class Input(
    val transactionHash: Hash,
    val outputIndex: OutputIndex
) {
    fun toHexString() = "${transactionHash.hash}${outputIndex.toHexString()}"
}
