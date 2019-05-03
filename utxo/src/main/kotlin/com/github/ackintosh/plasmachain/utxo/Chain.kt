package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Hash
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction

class Chain {
    val data = listOf(GENESIS_BLOCK)

    fun dumpAliceInformation() =
        mapOf(
            "address" to ALICE_ADDRESS.value,
            "private_key(hex encoded)" to ALICE_KEY_PAIR.private.encoded.toHexString(),
            "public_key(hex encoded)" to ALICE_KEY_PAIR.public.encoded.toHexString()
        )

    companion object {
        private val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)

        private val GENESIS_BLOCK = generateGenesisBlock()

        private fun generateGenesisBlock(): Block {
            val transactions = listOf(
                Transaction(
                    inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
                    outputs = listOf(Output(100, ALICE_ADDRESS))
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