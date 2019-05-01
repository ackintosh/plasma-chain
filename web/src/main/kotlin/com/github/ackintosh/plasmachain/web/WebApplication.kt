package com.github.ackintosh.plasmachain.web

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.Block
import com.github.ackintosh.plasmachain.utxo.BlockHeader
import com.github.ackintosh.plasmachain.utxo.PreviousBlockHash
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmachain.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmachain.utxo.transaction.Output
import com.github.ackintosh.plasmachain.web.proto.PlasmaChainGrpc
import com.github.ackintosh.plasmachain.web.proto.Response
import com.github.ackintosh.plasmachain.web.proto.Transaction
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@SpringBootApplication
class WebApplication

@RestController
class Index {
    @GetMapping("/")
    fun welcome() = "Welcome to Plasma Chain!"
}

@GRpcService
class PlasmaChainGRpcService : PlasmaChainGrpc.PlasmaChainImplBase() {
    override fun submitTransaction(request: Transaction, responseObserver: StreamObserver<Response>?) {
        logger.info(request.toString())
        super.submitTransaction(request, responseObserver)
    }

    companion object {
        private val logger = Logger.getLogger(PlasmaChainGRpcService::class.java.name)
    }
}

class Chain {
    companion object {
        private val ALICE = Address.from(Address.generateKeyPair())

        private val GENESIS_BLOCK = {
            val transactions = listOf(com.github.ackintosh.plasmachain.utxo.transaction.Transaction(
                inputs = listOf(GenerationInput(CoinbaseData("xxx"))),
                outputs = listOf(Output(100, ALICE))
            ))

            Block(
                header = BlockHeader(
                    previousBlockHash = PreviousBlockHash("0"),
                    merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
                ),
                transactions = transactions
            )
        }.invoke()

        val CHAIN = listOf(GENESIS_BLOCK)
    }
}

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
