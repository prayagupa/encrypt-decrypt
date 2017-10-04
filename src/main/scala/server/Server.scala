package server

import java.io.IOException
import java.security.spec.{InvalidKeySpecException, PKCS8EncodedKeySpec}
import java.security.{InvalidKeyException, KeyFactory, NoSuchAlgorithmException, PrivateKey}
import javax.crypto.{BadPaddingException, Cipher, IllegalBlockSizeException, NoSuchPaddingException}

import client.Client.readFileBytes

object Server {

  @throws[IOException]
  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeySpecException]
  private def readPrivateKey(filename: String): PrivateKey = {
    val keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename))
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePrivate(keySpec)
  }

  @throws[NoSuchAlgorithmException]
  @throws[NoSuchPaddingException]
  @throws[InvalidKeyException]
  @throws[IllegalBlockSizeException]
  @throws[BadPaddingException]
  def decrypt(privateKey: String, ciphertext: Array[Byte]): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
    cipher.init(Cipher.DECRYPT_MODE, readPrivateKey(privateKey))
    cipher.doFinal(ciphertext)
  }
}
