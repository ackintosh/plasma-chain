package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

@kotlin.ExperimentalUnsignedTypes
class Block(
    val header: Header,
    val merkleRoot: MerkleTree.Node,
    val number: BlockNumber,
    val transactions: List<Transaction>
) {
    fun blockHash() = BlockHash(
        Hashing.sha256()
            .hashString(
                Hashing.sha256()
                    .hashString(header.run { "${previousBlockHash.value}${merkleRoot.transactionHash.value}" }, StandardCharsets.UTF_8)
                    .toString(),
                StandardCharsets.UTF_8
            )
            .toString()
    )

    fun findOutput(transactionHash: TransactionHash, outputIndex: OutputIndex) : Output? {
        return transactions.filter { tx -> tx.transactionHash().equals(transactionHash) }
            .firstOrNull()
            ?.findOutput(outputIndex)
    }
}