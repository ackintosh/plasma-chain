package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionVerificationService
import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.http.HttpService
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
        logger.info("address: $ALICE_ADDRESS")
        logger.info("private key (hex encoded): ${ALICE_KEY_PAIR.private.encoded.toHexString()}")
        logger.info("public key (hex encoded): ${ALICE_KEY_PAIR.public.encoded.toHexString()}")
        logger.info("Genesis block hash: ${getGenesisBlock().blockHash().value}")

        subscribeRootChainEvents()
    }

    private fun subscribeRootChainEvents() {
        val web3 = Web3j.build(HttpService())
        val filter = EthFilter(
            DefaultBlockParameterName.EARLIEST,
            DefaultBlockParameterName.LATEST,
            ROOT_CHAIN_CONTRACT_ADDRESS
        )

        web3.ethLogFlowable(filter).subscribe { log ->
            val event = Event(
                "Deposited",
                listOf(
                    TypeReference.create(org.web3j.abi.datatypes.Address::class.java),
                    TypeReference.create(Uint256::class.java)
                )
            )
            log.topics.forEach { topic ->
                when (topic) {
                    EventEncoder.encode(event) -> {
                        val params = FunctionReturnDecoder.decode(
                            log.data,
                            event.nonIndexedParameters
                        )
                        println(params)
                        // TODO: handle Deposited event
                    }
                    else -> logger.info("Unhandled event. topic_signature: $topic")
                }
            }
        }
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)
        private const val ROOT_CHAIN_CONTRACT_ADDRESS = "0xF12b5dd4EAD5F743C6BaA640B0216200e89B60Da"
    }
}
