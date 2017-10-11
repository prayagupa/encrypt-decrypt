package server

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import sun.misc.BASE64Decoder

class SymmetricDecryptor(secretKey: String) extends Decryptor {

  val ALGORITHM = "AES"

  def decrypt(hexData: String): Either[Throwable, String] = {
    try {
      val key = new SecretKeySpec(secretKeyBytes(secretKey, 16), ALGORITHM)

      val c = Cipher.getInstance(ALGORITHM)

      c.init(Cipher.DECRYPT_MODE, key)

      val decorVal = new BASE64Decoder().decodeBuffer(hexData)
      val decValue = c.doFinal(decorVal)

      Right(new String(decValue))
    } catch {
      case ex: Exception => Left(ex)
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
