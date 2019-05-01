package com.github.ackintosh.plasmachain.utxo.transaction

import java.security.interfaces.ECPublicKey

class PublicKey {
    companion object {
        fun toString(key: ECPublicKey) = key.let {
            val point = it.w
            val x = adjustTo64(point.affineX.toString(16).toUpperCase())
            val y = adjustTo64(point.affineY.toString(16).toUpperCase())
            "04$x$y"
        }

        private fun adjustTo64(s: String) =
            when (s.length) {
                62 -> "00$s"
                63 -> "0$s"
                64 -> s
                else -> IllegalStateException()
            }
    }
}