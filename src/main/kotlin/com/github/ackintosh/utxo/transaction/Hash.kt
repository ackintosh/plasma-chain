package com.github.ackintosh.utxo.transaction

data class Hash(private val hash: String) {
    companion object {
        // TODO
        val GENERATION = Hash("0")
    }
}
