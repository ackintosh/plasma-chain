package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash

@kotlin.ExperimentalUnsignedTypes
class Block(
    val merkleRoot: MerkleTree.Node,
    val number: BlockNumber,
    val transactions: List<Transaction>
) {
    fun findOutput(transactionHash: TransactionHash, outputIndex: OutputIndex) : Output? {
        return transactions.filter { tx -> tx.transactionHash().equals(transactionHash) }
            .firstOrNull()
            ?.findOutput(outputIndex)
    }
}