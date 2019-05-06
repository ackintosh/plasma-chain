package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.Header
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
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
import java.math.BigInteger
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
            logger.info("New block has been added into the chain. block_hash: ${block.blockHash()}")
            transactionPool.clear()
            logger.info("Transaction pool has been cleared")
        } else {
            logger.warning("Failed to add new block. block_hash: ${block.blockHash()}")
            return false
        }

        return true
    }

    fun addTransaction(transaction: Transaction) =
        when (TransactionVerificationService.verify(chain, transaction)) {
            is TransactionVerificationService.Result.Success -> {
                val added = transactionPool.add(transaction)
                if (added) {
                    logger.info("Added transaction into tx pool: ${transaction.transactionHash()}")
                } else {
                    logger.warning("Failed to add transaction into tx pool: ${transaction.transactionHash()}")
                }
                added
            }
            is TransactionVerificationService.Result.Failure -> {
                logger.warning("The transaction is invalid: ${transaction.transactionHash()}")
                false
            }
        }

    fun getGenesisBlock() = chain.genesisBlock()

    fun getLatestBlock() = chain.latestBlock()

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
            logger.info("Event: $log")
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
                        val web3jAddress = params[0] as org.web3j.abi.datatypes.Address
                        val web3jAmount = params[1].value as BigInteger

                        logger.info("[Deposited] address:$web3jAddress amount:$web3jAmount")
                        handleDepositedEvent(Address.from(web3jAddress.toString()), web3jAmount)
                    }
                    else -> logger.info("Unhandled event. topic_signature: $topic")
                }
            }
        }
    }

    // TODO: race condition
    internal fun handleDepositedEvent(address: Address, amount: BigInteger) {
        val generationTransaction = Transaction(
            input1 = GenerationInput(CoinbaseData("xxx")),
            output1 = listOf(Output(amount, address))
        )
        logger.info("Generation transaction: ${generationTransaction.transactionHash().value}")

        val block = Block(
            header = Header(
                previousBlockHash = chain.latestBlock().blockHash(),
                merkleRoot = MerkleTree.build(listOf(generationTransaction.transactionHash()))
            ),
            transactions = listOf(generationTransaction)
        )
        if (chain.add(block)) {
            logger.info("A deposit block has been added into the chain successfully. block: ${block.blockHash()}")
        } else {
            logger.warning("Failed to add the the deposit block: ${block.blockHash()}")
        }
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)
        private const val ROOT_CHAIN_CONTRACT_ADDRESS = "0xF12b5dd4EAD5F743C6BaA640B0216200e89B60Da"
    }
}
