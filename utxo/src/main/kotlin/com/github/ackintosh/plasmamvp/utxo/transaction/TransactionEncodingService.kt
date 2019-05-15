package com.github.ackintosh.plasmamvp.utxo.transaction

import org.kethereum.functions.rlp.RLPElement
import org.kethereum.functions.rlp.RLPList
import org.kethereum.functions.rlp.encode
import org.kethereum.functions.rlp.toRLP

class TransactionEncodingService {
    companion object {
        @kotlin.ExperimentalUnsignedTypes
        fun encode(transaction: Transaction) : ByteArray {
            val rlpElements = transaction.let { tx ->
                val inputs = listOf(tx.input1, tx.input2).filterNotNull().fold(emptyList<RLPElement>()) { i1, i2 -> i1.plus(toRLP(i2)) }
                val outputs = listOf(tx.output1, tx.output2).filterNotNull().fold(emptyList<RLPElement>()) { o1, o2 -> o1.plus(toRLP(o2)) }
                inputs + outputs
            }

            return RLPList(rlpElements).encode()
        }

        @kotlin.ExperimentalUnsignedTypes
        private fun toRLP(transactionInput: TransactionInput) =
            when(transactionInput) {
                is Input -> {
                    listOf(
                        transactionInput.transactionHash().value.toRLP(),
                        transactionInput.outputIndex().index.toInt().toRLP()
                    )
                }
                is GenerationInput -> {
                    listOf(
                        transactionInput.transactionHash().value.toRLP(),
                        transactionInput.outputIndex().index.toInt().toRLP()
                    )
                }
        }

        private fun toRLP(output: Output) = listOf(
            output.address.toString().toRLP(), output.amount.toRLP()
        )
    }
}