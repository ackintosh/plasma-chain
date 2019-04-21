package com.github.ackintosh.utxo

import com.github.ackintosh.utxo.transaction.Transaction

class Block(
    private val header: BlockHeader,
    private val transactions: List<Transaction>
) {
    fun transactionCounter() = transactions.count()
}