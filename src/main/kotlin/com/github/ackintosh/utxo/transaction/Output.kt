package com.github.ackintosh.utxo.transaction

class Output(val amount: Int) {
    fun toHexString() = amount.toString(16).padStart(16, '0')
}