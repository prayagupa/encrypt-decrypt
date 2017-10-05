package client

import java.math.BigInteger
import java.nio.file.{Files, Paths}
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAPublicKeySpec
import java.security.{KeyFactory, PublicKey}
import java.util.Base64
import javax.crypto.Cipher

import scala.collection.JavaConverters._

object Client {

  private def readPublicKey(filename: String): PublicKey = {
    val modulusData = Files.readAllLines(Paths.get(filename)).asScala.toList
    val modulusBase64Ascii: String = modulusData.slice(1, modulusData.length - 1).mkString("")

    val modulus = new BigInteger(1, Base64.getDecoder.decode(modulusBase64Ascii))
    val pubExp = new BigInteger("010001", 16)

    val pubKeySpec = new RSAPublicKeySpec(modulus, pubExp)

      KeyFactory.getInstance("RSA").generatePublic(pubKeySpec).asInstanceOf[RSAPublicKey]
  }

  def enc(publicKey: String, data: String): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
    cipher.init(Cipher.ENCRYPT_MODE, readPublicKey(publicKey))
    cipher.doFinal(data.getBytes())
  }
}
