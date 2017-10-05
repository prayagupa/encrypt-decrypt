package client

import java.math.BigInteger

import org.scalatest.{FunSuite, Matchers}

class ClientSpecs extends FunSuite with Matchers {

  test("given private key, encrypts data") {

    Client.enc("src/main/resources/keypair_DER/public_key.der", "data to encrypt").foreach(e => {
      val hex = new BigInteger(e).toString(16)

      println(hex)
    })
  }
}
