package client

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import sun.misc.BASE64Encoder

class SymmetricEncryptor(secretKey: String) extends Encryptor {

  private val ALGORITHM = "AES"

  def enc(data: String): Option[String] = {
    try {
      val key = new SecretKeySpec(secretKeyBytes(secretKey, 16), "AES")
      val c = Cipher.getInstance(ALGORITHM)
      c.init(Cipher.ENCRYPT_MODE, key)
      val encValue = c.doFinal(data.getBytes)
      Option(new BASE64Encoder().encode(encValue))
    } catch {
      case ex: Exception => None
    }
  }

  /**
    * adds blank space if secretKey is smaller than wanted key size
    */
  private def secretKeyBytes(secretKeyStr: String, length: Int) = {
    var secretKeyCopy = secretKeyStr

    def secretKeyIsSmallerThanBitSize = {
      secretKeyCopy.length < length
    }

    if (secretKeyIsSmallerThanBitSize) {
      val missingLength = length - secretKeyStr.length
      var index = 0
      while (index < missingLength) {
        secretKeyCopy += " "
        index = index + 1
      }
    }
    secretKeyCopy.substring(0, length).getBytes("UTF-8")
  }
}
