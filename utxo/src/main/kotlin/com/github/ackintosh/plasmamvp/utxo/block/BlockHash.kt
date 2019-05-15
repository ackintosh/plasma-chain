package com.github.ackintosh.plasmamvp.utxo.block

import com.github.ackintosh.plasmamvp.utxo.extensions.toHexString

data class BlockHash(val value: String) {
    companion object {
        fun zero() = BlockHash(
            ByteArray(32) { _ -> 0 }.toHexString()
        )
    }
}
