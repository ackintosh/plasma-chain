package com.github.ackintosh.plasmachain.web

import com.github.ackintosh.plasmachain.web.proto.PlasmaChainGrpc
import com.github.ackintosh.plasmachain.web.proto.Response
import com.github.ackintosh.plasmachain.web.proto.Transaction
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class WebApplication

@RestController
class Index {
    @GetMapping("/")
    fun welcome() = "Welcome to Plasma Chain!"
}

@GRpcService
class PlasmaChainGRpcService : PlasmaChainGrpc.PlasmaChainImplBase() {
    override fun submitTransaction(request: Transaction?, responseObserver: StreamObserver<Response>?) {
        super.submitTransaction(request, responseObserver)
    }
}

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
