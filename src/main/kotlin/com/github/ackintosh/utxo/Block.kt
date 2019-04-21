package com.github.ackintosh.utxo

class Block(
    private val transactions: List<Transaction>
) {
    fun transactionCounter() = transactions.count()
}