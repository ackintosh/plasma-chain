package com.github.ackintosh.plasmachain.utxo.extensions

fun ByteArray.toHexString() : String =
        this.map { String.format("%02X", it) }.joinToString(separator = "")
