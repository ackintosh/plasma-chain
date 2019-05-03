package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import java.util.logging.Logger

class Node : Runnable {
    override fun run() {
        onStart()
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
        private val TRANSACTION_POOL : MutableList<com.github.ackintosh.plasmachain.utxo.transaction.Transaction> = mutableListOf()
        private val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)
        private val CHAIN = Chain(ALICE_ADDRESS)

        fun getGenesisBlock() = CHAIN.data.first()
        fun addTransaction(transaction: com.github.ackintosh.plasmachain.utxo.transaction.Transaction) = TRANSACTION_POOL.add(transaction)
    }
}
