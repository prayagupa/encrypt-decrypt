package client

import java.math.BigInteger

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AsymmetricEncryptorSpecs extends AnyFunSuite with Matchers {

  test("given private key, encrypts data") {

    new AsymmetricEncryptor("src/main/resources/keypair_DER/public_key.der").enc("data to encrypt").foreach(hex => {

      println(hex)
    })
  }
}
