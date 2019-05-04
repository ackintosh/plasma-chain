package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Hash
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.*

class Chain(val data: MutableList<Block>) {
    fun add(block: Block) = data.add(block)

    fun latestBlock() = data.last()

    fun findOutput(transactionHash: com.github.ackintosh.plasmachain.utxo.transaction.Hash, outputIndex: OutputIndex) : Output? {
        val output = data.forEach {
            val o = it.findOutput(transactionHash, outputIndex)
            if (o != null) {
                return o
            }
        }

        return null
    }

    companion object {
        fun from(address: Address) = Chain(mutableListOf(generateGenesisBlock(address)))

        private fun generateGenesisBlock(address: Address): Block {
            val transactions = listOf(
                Transaction(
                    inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
                    outputs = listOf(Output(100, address))
                )
            )

            return Block(
                header = Header(
                    previousBlockHash = Hash.zero(),
                    merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
                ),
                transactions = transactions
            )
        }
    }
}