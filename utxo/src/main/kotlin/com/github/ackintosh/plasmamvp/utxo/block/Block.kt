package com.github.ackintosh.plasmamvp.utxo.block

import com.github.ackintosh.plasmamvp.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmamvp.utxo.transaction.Output
import com.github.ackintosh.plasmamvp.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmamvp.utxo.transaction.Transaction
import com.github.ackintosh.plasmamvp.utxo.transaction.TransactionHash

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