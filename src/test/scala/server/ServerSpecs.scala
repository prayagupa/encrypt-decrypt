package server

import client.Client
import org.scalatest.{FunSuite, Matchers}

class ServerSpecs extends FunSuite with Matchers {

  test("given private key, decrypts the bytes") {
    new String(Server.decrypt("src/main/resources/keypair_PEM/private_key.pem",
      Client.enc("src/main/resources/keypair_PEM/public_key.pem", "my data"))) shouldBe "my data"
  }
}
