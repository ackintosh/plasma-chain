package com.github.ackintosh.plasmachain.utxo

import com.google.common.hash.Hashing
import org.bitcoinj.core.Base58
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.charset.StandardCharsets
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.Security
import java.security.interfaces.ECPublicKey
import java.security.spec.ECGenParameterSpec

class Address(val value: String) {
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
            val publicKeyString = publicKey.let {
                val point = it.w
                val x = adjustTo64(point.affineX.toString(16).toUpperCase())
                val y = adjustTo64(point.affineY.toString(16).toUpperCase())
                "04$x$y"
            }

            val s1 = Hashing.sha256().hashString(publicKeyString, StandardCharsets.UTF_8)

            Security.addProvider(BouncyCastleProvider())
            val rmd = MessageDigest.getInstance("RipeMD160", BouncyCastleProvider.PROVIDER_NAME)
            val r1 = rmd.digest(s1.asBytes())

            val r2 = ByteArray(r1.size + 1)
            r2[0] = 0
            for ((i, b) in r1.withIndex()) {
                r2[i + 1] = b
            }


            val s2 = Hashing.sha256().hashString(s1.toString(), StandardCharsets.UTF_8)
            val s3 = Hashing.sha256().hashString(s2.toString(), StandardCharsets.UTF_8)

            val checksum = s3.asBytes().copyOfRange(0, 4)

            return Address(Base58.encode(r2 + checksum))
        }

        private fun adjustTo64(s: String) =
            when (s.length) {
                62 -> "00$s"
                63 -> "0$s"
                64 -> s
                else -> IllegalStateException()
            }
    }
}
