package client

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import sun.misc.BASE64Encoder

trait Encryptor {
  def enc(data: String): Option[String]
}
