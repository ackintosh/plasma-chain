package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString

data class TransactionHash(val value: String) {
    companion object {
        val GENERATION = TransactionHash(
            (ByteArray(32) { 0.toByte() }).toHexString()
        )
        val ZERO = GENERATION
    }
}
