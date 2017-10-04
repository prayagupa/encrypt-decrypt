package server

import client.Client
import org.scalatest.{FunSuite, Matchers}

class ServerSpecs extends FunSuite with Matchers {

  test("given private key, decrypts the bytes") {
    new String(Server.decrypt("src/main/resources/keypair_DER/private.der",
      Client.enc("src/main/resources/keypair_DER/public.der", "my data"))) shouldBe "my data"
  }
}
