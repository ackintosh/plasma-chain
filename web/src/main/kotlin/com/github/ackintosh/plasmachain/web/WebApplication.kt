package com.github.ackintosh.plasmachain.web

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

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
