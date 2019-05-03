package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Chain
import java.util.logging.Logger

class Node : Runnable {
    override fun run() {
        onStart()
    }

    private fun onStart() {
        logger.info("Started Plasma Chain node")
        CHAIN.dumpAliceInformation().forEach({k, v -> logger.info("$k: $v")})
        logger.info("Genesis block hash: ${getGenesisBlock().blockHash().value}")
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        private val TRANSACTION_POOL : MutableList<com.github.ackintosh.plasmachain.utxo.transaction.Transaction> = mutableListOf()
        private val CHAIN = Chain()

        fun getGenesisBlock() = CHAIN.data.first()
        fun addTransaction(transaction: com.github.ackintosh.plasmachain.utxo.transaction.Transaction) = TRANSACTION_POOL.add(transaction)
    }
}
