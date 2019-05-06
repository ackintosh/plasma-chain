package com.github.ackintosh.plasmachain.utxo.block

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmachain.utxo.transaction.Input
import com.github.ackintosh.plasmachain.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmachain.utxo.transaction.Signature
import com.github.ackintosh.plasmachain.utxo.transaction.Transaction
import com.github.ackintosh.plasmachain.utxo.transaction.TransactionHash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPublicKey

class BlockTest {
    @Test
    fun blockHash() {
        val input = Input(
            transactionHash = TransactionHash.GENERATION,
            outputIndex = OutputIndex.GENERATION,
            signature = Signature("xxx"),
            publicKey = Address.generateKeyPair().public as ECPublicKey
        )
        val transactions = listOf(
            Transaction(input1 = input, outputs = emptyList()),
            Transaction(input1 = input, outputs = emptyList())
        )

        val block = Block(
            header = Header(
                previousBlockHash = BlockHash("xxx"),
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() })
            ),
            transactions = transactions
        )

        assertEquals("c4b7c3cf8cfcad9d85abf63d7452fc1c4174a2c95fed8ce6366fc6dadc65d2f1", block.blockHash().value)
    }
}