package com.github.ackintosh.plasmamvp.utxo.transaction

import java.math.BigInteger

// 4byte
@kotlin.ExperimentalUnsignedTypes
class OutputIndex(val index: UInt) {
    fun toHexString() = index.toString(16).padStart(8, '0')

    companion object {
        val GENERATION = OutputIndex(0xFFFFFFFFu)

        fun from(bigInteger: BigInteger) = OutputIndex(bigInteger.toString().toUInt())
    }
}