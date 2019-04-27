package com.github.ackintosh.plasmachain.utxo.transaction

class Input(
    val transactionHash: Hash,
    val outputIndex: OutputIndex
) {
    fun toHexString() = "${transactionHash.hash}${outputIndex.toHexString()}"
}
