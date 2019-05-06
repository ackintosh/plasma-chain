package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.BlockHash
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import java.math.BigInteger

class Chain(private val data: MutableList<Block>) {
    fun add(block: Block) = data.add(block)

    fun genesisBlock() = data.first()
    fun latestBlock() = data.last()

    fun snapshot() = Chain(data.toMutableList())

    fun findOutput(transactionHash: TransactionHash, outputIndex: OutputIndex) : Output? {
        data.forEach {
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
                    input1 = GenerationInput(CoinbaseData("xxx")),
                    output1 = Output(BigInteger("100"), address)
                )
            )

            return Block(
                header = Header(
                    previousBlockHash = BlockHash.zero(),
                    merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
                ),
                transactions = transactions
            )
        }
    }
}