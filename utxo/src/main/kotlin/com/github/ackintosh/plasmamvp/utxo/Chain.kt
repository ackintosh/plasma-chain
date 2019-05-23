package com.github.ackintosh.plasmamvp.utxo

import com.github.ackintosh.plasmamvp.utxo.block.Block
import com.github.ackintosh.plasmamvp.utxo.block.BlockNumber
import com.github.ackintosh.plasmamvp.utxo.merkletree.MerkleTree
import com.github.ackintosh.plasmamvp.utxo.transaction.CoinbaseData
import com.github.ackintosh.plasmamvp.utxo.transaction.GenerationInput
import com.github.ackintosh.plasmamvp.utxo.transaction.Output
import com.github.ackintosh.plasmamvp.utxo.transaction.OutputIndex
import com.github.ackintosh.plasmamvp.utxo.transaction.Transaction
import com.github.ackintosh.plasmamvp.utxo.transaction.TransactionHash
import java.math.BigInteger
import java.util.SortedMap

@kotlin.ExperimentalUnsignedTypes
class Chain(private val data: SortedMap<UInt, Block>) {
    // TODO: race condition
    private var currentBlockNumber = 1u
    private val childBlockNumberInterval = 1000u
    private var nextChildBlockNumber = childBlockNumberInterval

    fun currentBlockNumber() = BlockNumber(currentBlockNumber)
    fun nextChildBlockNumber() = BlockNumber(nextChildBlockNumber)
    fun updateNextChildBlockNumber() {
        nextChildBlockNumber += childBlockNumberInterval
    }

    fun add(block: Block) = data.put(block.number.value, block)

    fun genesisBlock() = data[0u] ?: throw IllegalStateException("Genesis block don't exist")
    fun latestBlock() = data[data.lastKey()] ?: throw IllegalStateException("No blocks")

    fun snapshot() = Chain(data.toSortedMap())

    fun findOutput(transactionHash: TransactionHash, outputIndex: OutputIndex) : Output? {
        data.forEach {
            val o = it.value.findOutput(transactionHash, outputIndex)
            if (o != null) {
                return o
            }
        }

        return null
    }

    fun markAsExitStarted(
        depositBlockNumber: BlockNumber,
        transactionIndex: BigInteger,
        outputIndex: OutputIndex
    ) : MarkAsExitStarted =
        data[depositBlockNumber.value]
            ?.transactions
            ?.get(transactionIndex.toInt())
            ?.findOutput(outputIndex)
            ?.run {
                this.markAsExitStarted()
                MarkAsExitStarted.Success()
            } ?: MarkAsExitStarted.NotFound()

    sealed class MarkAsExitStarted {
        class Success : Chain.MarkAsExitStarted()
        class NotFound : Chain.MarkAsExitStarted()
    }

    companion object {
        fun from(address: Address) = Chain(
            sortedMapOf(
                Pair(
                    0u,
                    generateGenesisBlock(address)
                )
            )
        )

        @kotlin.ExperimentalUnsignedTypes
        private fun generateGenesisBlock(address: Address): Block {
            val transactions = listOf(
                Transaction(
                    input1 = GenerationInput(CoinbaseData("xxx")),
                    output1 = Output(BigInteger("100"), address)
                )
            )

            return Block(
                merkleRoot = MerkleTree.build(transactions.map { it.transactionHash() }),
                number = BlockNumber(0u),
                transactions = transactions
            )
        }
    }
}