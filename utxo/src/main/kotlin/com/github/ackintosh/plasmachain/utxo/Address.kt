package com.github.ackintosh.plasmachain.utxo

import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import org.kethereum.keccakshortcut.keccak
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.ECPublicKey
import java.security.spec.ECGenParameterSpec

class Address(private val value: ByteArray) {
    override fun toString() = "0x${rawString()}"

    fun rawString() = value.toHexString()

    companion object {
        fun generateKeyPair() : KeyPair {
            // https://www.novixys.com/blog/generate-bitcoin-addresses-java/#3_Generate_an_ECDSA_Key_Pair
            //
            // val keyPair = Address.generateKeyPair()
            // val privateKey = keyPair.private as ECPrivateKey
            // val privateKeyString = adjustTo64(privateKey.s.toString(16).toUpperCase())

            val keyPairGenerator = KeyPairGenerator.getInstance("EC")
            val ecSpec = ECGenParameterSpec("secp256k1")
            keyPairGenerator.initialize(ecSpec)

            return keyPairGenerator.generateKeyPair()
        }

        fun from(keyPair: KeyPair) : Address {
            // https://www.novixys.com/blog/generate-bitcoin-addresses-java/#3_Generate_an_ECDSA_Key_Pair
            //
            // val keyPair = Address.generateKeyPair()
            // Address.generateFrom(keyPair)

            val publicKey = keyPair.public as ECPublicKey
            // 20byte
            return Address(
                publicKey.encoded.keccak().copyOfRange(12, 32)
            )
        }
    }
}
