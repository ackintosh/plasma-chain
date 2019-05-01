package com.github.ackintosh.plasmachain.utxo.block

class Hash(val value: String) {
    companion object {
        fun zero() = Hash(
            ByteArray(32) { _ -> 0 }.map { String.format("%02X", it) }.joinToString("")
        )
    }
}
