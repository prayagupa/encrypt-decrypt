package client

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class SymmetricEncryptor(secretKey: String) extends Encryptor {

  private val ALGORITHM = "AES"

  def enc(data: String): Option[String] = {
    try {
      val key = new SecretKeySpec(secretKeyBytes(secretKey, 16), "AES")
      val c = Cipher.getInstance(ALGORITHM)
      c.init(Cipher.ENCRYPT_MODE, key)
      val encValue = c.doFinal(data.getBytes)
      Option(java.util.Base64.getEncoder.encodeToString(encValue))
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
