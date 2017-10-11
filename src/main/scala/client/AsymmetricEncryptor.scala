package client

import java.math.BigInteger
import java.nio.file.{Files, Paths}
import java.security.spec.X509EncodedKeySpec
import java.security.{KeyFactory, PublicKey}
import javax.crypto.Cipher

class AsymmetricEncryptor(val publicKey: String) extends Encryptor {

  private def readPublicKey(filename: String): Option[PublicKey] = {
    val x509PublicSpec = new X509EncodedKeySpec(Files.readAllBytes(Paths.get(filename)))
    val keyFactory = KeyFactory.getInstance("RSA")

    Option(keyFactory.generatePublic(x509PublicSpec))
  }

  def enc(data: String): Option[String] = {

    readPublicKey(publicKey).map(p => {
      val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
      cipher.init(Cipher.ENCRYPT_MODE, p)
      new BigInteger(cipher.doFinal(data.getBytes())).toString(16)
    })
  }
}

