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
    private val transactionPool : MutableList<Transaction> = mutableListOf()
    private val chain = Chain.from(ALICE_ADDRESS)

    override fun run() {
        onStart()

        while (true) {
            Thread.sleep(3000)
            createNewBlock()
        }
    }

    // TODO: race condition
    fun createNewBlock() : Boolean {
        if (transactionPool.isEmpty()) {
            logger.info("transaction pool is empty")
            return true
        }

        logger.info("Creating a new block...")
        val transactions = transactionPool.subList(0, transactionPool.size)
        // TODO: verify transactions
        val block = Block(
            header = Header(
                previousBlockHash = chain.latestBlock().blockHash(),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )
        logger.info("New block: ${block.blockHash()}")

        if (chain.add(block)) {
            logger.info("New block has been added into the chain")
            transactionPool.clear()
            logger.info("Transaction pool has been cleared")
        } else {
            logger.warning("Failed to add new block")
            return false
        }

        return true
    }

    fun addTransaction(transaction: Transaction) =
        when (TransactionVerificationService.verify(chain, transaction)) {
            is TransactionVerificationService.Result.Success -> transactionPool.add(transaction)
            is TransactionVerificationService.Result.Failure -> false
        }

    fun getGenesisBlock() = chain.genesisBlock()

    private fun onStart() {
        logger.info("Started Plasma Chain node")
        logger.info("address: ${ALICE_ADDRESS.value}")
        logger.info("private key (hex encoded): ${ALICE_KEY_PAIR.private.encoded.toHexString()}")
        logger.info("public key (hex encoded): ${ALICE_KEY_PAIR.public.encoded.toHexString()}")
        logger.info("Genesis block hash: ${getGenesisBlock().blockHash().value}")
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)
    }
}
