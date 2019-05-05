package com.github.ackintosh.plasmachain.utxo.extensions

fun String.hexStringToByteArray() : ByteArray {
        return this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}

fun String.stripHexPrefix() = if (containsHexPrefix(this)) this.substring(2 until this.length) else this

private fun containsHexPrefix(s: String) : Boolean = s.startsWith("0x")
