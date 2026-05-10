package server

trait Decryptor {
  def decrypt(hexData: String): Either[Throwable, String]
}
