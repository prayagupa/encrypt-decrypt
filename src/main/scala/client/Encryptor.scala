package client

trait Encryptor {
  def enc(data: String): Option[String]
}
