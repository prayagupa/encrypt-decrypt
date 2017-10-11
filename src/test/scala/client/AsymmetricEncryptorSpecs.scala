package client

import java.math.BigInteger

import org.scalatest.{FunSuite, Matchers}

class AsymmetricEncryptorSpecs extends FunSuite with Matchers {

  test("given private key, encrypts data") {

    new AsymmetricEncryptor("src/main/resources/keypair_DER/public_key.der").enc("data to encrypt").foreach(hex => {

      println(hex)
    })
  }
}
