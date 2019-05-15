package com.github.ackintosh.plasmamvp.utxo.block

import java.math.BigInteger

@kotlin.ExperimentalUnsignedTypes
data class BlockNumber(val value: UInt) {
    companion object {
        fun from(bigInteger: BigInteger) =
            BlockNumber(bigInteger.toString().toUInt())
    }
}