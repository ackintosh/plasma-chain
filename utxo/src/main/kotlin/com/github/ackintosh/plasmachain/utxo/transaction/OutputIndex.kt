package com.github.ackintosh.plasmachain.utxo.transaction

// 4byte
class OutputIndex(@UseExperimental(ExperimentalUnsignedTypes::class) val index: UInt) {
    fun toHexString() = index.toString(16).padStart(8, '0')

    companion object {
        val GENERATION = OutputIndex(0xFFFFFFFFu)
    }
}