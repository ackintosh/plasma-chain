package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address

class Output(val amount: Int, private val address: Address) {
    fun lockingScript() : String = "OP_DUP OP_HASH160 ${address.to20BytePublicKeyHash()} OP_EQUALVERIFY OP_CHECKSIG"

    fun toHexString() = amount.toString(16).padStart(16, '0')
}