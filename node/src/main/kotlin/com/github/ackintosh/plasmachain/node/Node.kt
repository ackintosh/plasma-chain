package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionVerificationService
import java.util.logging.Logger

class Node : Runnable {
    override fun run() {
        onStart()

        while (true) {
            Thread.sleep(3000)
            createNewBlock()
        }
    }

    // TODO: race condition
    fun createNewBlock() : Boolean {
        if (TRANSACTION_POOL.isEmpty()) {
            logger.info("transaction pool is empty")
            return true
        }

        logger.info("Creating a new block...")
        val transactions = TRANSACTION_POOL.subList(0, TRANSACTION_POOL.size)
        // TODO: verify transactions
        val block = Block(
            header = Header(
                previousBlockHash = CHAIN.latestBlock().blockHash(),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )
        logger.info("New block: ${block.blockHash()}")

        if (CHAIN.add(block)) {
            logger.info("New block has been added into the chain")
            TRANSACTION_POOL.clear()
            logger.info("Transaction pool has been cleared")
        } else {
            logger.warning("Failed to add new block")
            return false
        }

        return true
    }

    private fun onStart() {
        logger.info("Started Plasma Chain node")
        logger.info("address: ${ALICE_ADDRESS.value}")
        logger.info("private key (hex encoded): ${ALICE_KEY_PAIR.private.encoded.toHexString()}")
        logger.info("public key (hex encoded): ${ALICE_KEY_PAIR.public.encoded.toHexString()}")
        logger.info("Genesis block hash: ${getGenesisBlock().blockHash().value}")
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        private val TRANSACTION_POOL : MutableList<Transaction> = mutableListOf()
        val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)
        private val CHAIN = Chain.from(ALICE_ADDRESS)

        fun getGenesisBlock() = CHAIN.data.first()

        fun addTransaction(transaction: Transaction) =
            when (TransactionVerificationService.verify(CHAIN, transaction)) {
                is TransactionVerificationService.Result.Success -> TRANSACTION_POOL.add(transaction)
                is TransactionVerificationService.Result.Failure -> false
            }
    }
}
