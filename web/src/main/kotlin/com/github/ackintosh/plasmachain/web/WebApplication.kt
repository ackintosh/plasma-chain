package com.github.ackintosh.plasmachain.web

import com.github.ackintosh.plasmachain.utxo.Chain
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

// TODO: This will be an individual module
class Node : Runnable {
    override fun run() {
        logger.info("Started Plasma Chain node")
    }

    companion object {
        private val logger = Logger.getLogger(Node::class.java.name)
        private val TRANSACTION_POOL : MutableList<com.github.ackintosh.plasmachain.utxo.transaction.Transaction> = mutableListOf()
        private val CHAIN = Chain()

        fun getGenesisBlock() = CHAIN.data.first()
        fun addTransaction(transaction: com.github.ackintosh.plasmachain.utxo.transaction.Transaction) = TRANSACTION_POOL.add(transaction)
    }
}

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
    Thread(Node()).start()
}
