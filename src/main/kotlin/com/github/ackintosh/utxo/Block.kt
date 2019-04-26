package com.github.ackintosh.utxo

import com.github.ackintosh.utxo.transaction.Transaction
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Block(
    private val header: BlockHeader,
    private val transactions: List<Transaction>
) {
    fun transactionCounter() = transactions.count()

    fun blockHash() =
        Hashing.sha256()
            .hashString(
                Hashing.sha256()
                    .hashString(header.run { "$previousBlockHash${merkleRoot.hash.hash}" }, StandardCharsets.UTF_8)
                    .toString(),
                StandardCharsets.UTF_8
            )
            .toString()
}