package com.github.ackintosh.plasmachain.utxo.transaction

import com.github.ackintosh.plasmachain.utxo.Address
import java.math.BigInteger

class Output(val amount: BigInteger, private val address: Address) {
    fun lockingScript() : String = "OP_DUP OP_HASH160 ${address.rawString()} OP_EQUALVERIFY OP_CHECKSIG"

    fun toHexString() = amount.toString(16).padStart(16, '0')
}