package com.github.ackintosh.plasmachain.utxo.extensions

fun String.hexStringToByteArray() : ByteArray =
        this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
