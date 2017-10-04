package client

import javax.crypto.Cipher
import java.io.IOException
import java.nio.file.{Files, Paths}

import java.io.IOException
import java.security.spec.{InvalidKeySpecException, X509EncodedKeySpec}
import java.security.{KeyFactory, NoSuchAlgorithmException, PublicKey}

object Client {

  @throws[IOException]
  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeySpecException]
  private def readPublicKey(filename: String): PublicKey = {
    val publicSpec = new X509EncodedKeySpec(readFileBytes(filename))
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePublic(publicSpec)
  }

  @throws[IOException]
  def readFileBytes(filename: String): Array[Byte] = {
    val path = Paths.get(filename)
    Files.readAllBytes(path)
  }

  def enc(publicKey: String, data: String): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, readPublicKey(publicKey));
    cipher.doFinal(data.getBytes())
  }
}
