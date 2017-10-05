package client

import java.math.BigInteger

import org.scalatest.{FunSuite, Matchers}

class ClientSpecs extends FunSuite with Matchers {

  test("given private key, encrypts data") {

    val enc = Client.enc("src/main/resources/keypair_PEM/public_key.pem", "data to encrypt")

    val hex = new BigInteger(enc).toString(16)

    println(hex)
  }
}
