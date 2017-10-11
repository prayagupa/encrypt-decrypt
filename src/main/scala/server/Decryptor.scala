package server

import sun.misc.BASE64Decoder
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

trait Decryptor {
  def decrypt(hexData: String): Either[Throwable, String]
}
