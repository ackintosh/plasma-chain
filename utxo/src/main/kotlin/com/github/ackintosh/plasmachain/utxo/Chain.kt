package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Hash
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction

class Chain {
    val data = listOf(GENESIS_BLOCK)

    companion object {
        private val ALICE = Address.from(Address.generateKeyPair())

        private val GENESIS_BLOCK = generateGenesisBlock()

        private fun generateGenesisBlock(): Block {
            val transactions = listOf(
                Transaction(
                    inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
                    outputs = listOf(Output(100, ALICE))
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