package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.node.web3j.RootChain
import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Chain
import com.github.ackintosh.plasmachain.utxo.block.Block
import com.github.ackintosh.plasmachain.utxo.block.BlockNumber
import com.github.ackintosh.plasmachain.utxo.extensions.hexStringToByteArray
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionVerificationService
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.util.logging.Logger

@kotlin.ExperimentalUnsignedTypes
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
        // TODO: consistency of block number
        val block = Block(
            merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() }),
            number = chain.nextChildBlockNumber(),
            transactions = transactions
        )
        logger.info("New block: $block")

        if (chain.add(block)) {
            logger.info("New block has been added into the chain. block_hash: $block")

            block.run {
                val transactionReceipt = rootChain().submit(
                    this.merkleRoot.transactionHash.value.hexStringToByteArray(),
                    BigInteger(this.number.value.toString())
                ).send()
                logger.info("Submitted the plasma block to root chain. transaction receipt: $transactionReceipt")
            }

            chain.updateNextChildBlockNumber()
            transactionPool.clear()
            logger.info("Transaction pool has been cleared")
        } else {
            logger.warning("Failed to add new block. block_hash: $block")
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
        logger.info("Genesis block hash: ${getGenesisBlock()}")

        subscribeRootChainEvents()
    }

    private fun subscribeRootChainEvents() {
        rootChain().run {
            // DepositCreated
            this
                .depositCreatedEventFlowable(
                    DefaultBlockParameterName.EARLIEST,
                    DefaultBlockParameterName.LATEST
                )
                .subscribe({ log ->
                    logger.info("[DepositCreated] $log")

                    handleDepositedEvent(
                        Address.from(log.owner),
                        log.amount,
                        BlockNumber.from(log.blockNumber)
                    )
                }, { throw it }) // TODO: error handling

            // BlockSubmitted
            this
                .blockSubmittedEventFlowable(
                    DefaultBlockParameterName.EARLIEST,
                    DefaultBlockParameterName.LATEST
                )
                .subscribe({ log ->
                    logger.info("[BlockSubmitted] merkleRoot:${log.blockRoot.toHexString()}")
                }, { throw it }) // TODO: error handling

            // ExitStarted
            this
                .exitStartedEventFlowable(
                    DefaultBlockParameterName.EARLIEST,
                    DefaultBlockParameterName.LATEST
                ).subscribe({ log ->
                    logger.info("[ExitStarted] owner:${log.owner} blockNumber: ${log.blockNumber}")
                }, { throw it }) // TODO: error handling
        }
    }

    // TODO: race condition
    internal fun handleDepositedEvent(address: Address, amount: BigInteger, depositBlockNumber: BlockNumber) {
        val generationTransaction = Transaction(
            input1 = GenerationInput(CoinbaseData("xxx")),
            output1 = Output(amount, address)
        )
        logger.info("Generation transaction: ${generationTransaction.transactionHash().value}")

        // TODO: obtain a block number from event data
        // TODO: consistency of block number
        val block = Block(
            merkleRoot = MerkleTree.build(listOf(generationTransaction.transactionHash())),
            number = depositBlockNumber,
            transactions = listOf(generationTransaction)
        )
        if (chain.add(block)) {
            logger.info("A deposit block has been added into plasma chain successfully. block: $block")
        } else {
            logger.warning("Failed to add the the deposit block: $block")
        }
    }

    private fun web3() = Web3j.build(HttpService())

    private fun rootChain() =
        RootChain.load(
            RootChain.getPreviouslyDeployedAddress(ROOT_CHAIN_CONTRACT_NETWORK_ID),
            web3(),
            ClientTransactionManager(web3(), "0xf17f52151EbEF6C7334FAD080c5704D77216b732"),
            DefaultGasProvider()
        )

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        val ALICE_KEY_PAIR = Address.generateKeyPair()
        private val ALICE_ADDRESS = Address.from(ALICE_KEY_PAIR)

        // see contract/build/contracts/RootChain.json
        private const val ROOT_CHAIN_CONTRACT_NETWORK_ID = "1557505324562"
    }
}
