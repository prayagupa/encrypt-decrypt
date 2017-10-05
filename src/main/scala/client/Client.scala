package client

import java.math.BigInteger
import java.nio.file.{Files, Paths}
import java.security.interfaces.RSAPublicKey
import java.security.spec.{RSAPublicKeySpec, X509EncodedKeySpec}
import java.security.{KeyFactory, PublicKey}
import java.util.Base64
import javax.crypto.Cipher

import scala.collection.JavaConverters._

object Client {

  private def readPublicKey(filename: String): PublicKey = {
    val x509PublicSpec = new X509EncodedKeySpec(Files.readAllBytes(Paths.get(filename)))
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePublic(x509PublicSpec)
  }

  def enc(publicKey: String, data: String): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
    cipher.init(Cipher.ENCRYPT_MODE, readPublicKey(publicKey))
    cipher.doFinal(data.getBytes())
  }
}
