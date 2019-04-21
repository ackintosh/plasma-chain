package com.github.ackintosh.utxo.transaction

data class OutputIndex(@UseExperimental(ExperimentalUnsignedTypes::class) private val index: UInt) {
    companion object {
        val GENERATION = OutputIndex(0xFFFFFFFFu)
    }
}