package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString

class Hash(val value: String) {
    companion object {
        fun zero() = Hash(
            ByteArray(32) { _ -> 0 }.toHexString()
        )
    }
}
