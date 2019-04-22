package com.github.ackintosh.utxo.transaction

data class Hash(val hash: String) {
    companion object {
        // TODO
        val GENERATION = Hash("0")
    }
}
