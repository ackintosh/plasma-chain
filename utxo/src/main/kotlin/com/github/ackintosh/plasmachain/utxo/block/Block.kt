package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Block(
    private val header: Header,
    private val transactions: List<Transaction>
) {
    fun transactionCounter() = transactions.count()

    fun blockHash() = Hash(
        Hashing.sha256()
            .hashString(
                Hashing.sha256()
                    .hashString(header.run { "${previousBlockHash.value}${merkleRoot.hash.value}" }, StandardCharsets.UTF_8)
                    .toString(),
                StandardCharsets.UTF_8
            )
            .toString()
    )
}