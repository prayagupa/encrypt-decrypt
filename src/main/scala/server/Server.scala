package server

import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net._
import java.nio.file.{Files, Paths}
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyFactory, PrivateKey}
import javax.crypto.Cipher
import javax.xml.bind.DatatypeConverter

import com.typesafe.config.ConfigFactory

class Server {

  val config = ConfigFactory.load("application.conf")

  import Server._

  def start(): Unit = {
    val server = new ServerSocket(9999)

    val localhost = InetAddress.getLocalHost

    println("[INFO] starting a server at " + localhost.getHostAddress + ":9999")
    while (true) {

      val clientConnection = server.accept()
      val is = clientConnection.getInputStream
      val out = new PrintWriter(clientConnection.getOutputStream)
      val in = new BufferedReader(new InputStreamReader(is))
      var line: String = null
      line = in.readLine
      val request_method = line
      System.out.println("HTTP-HEADER: " + line)
      line = ""
      // looks for post data
      var postDataStartingIndex = -1
      while ( {
        (line = in.readLine) != null && (line.length != 0)
      }) {
        //println("HTTP-HEADER: " + line)
        if (line.indexOf("Content-Length:") > -1)
          postDataStartingIndex = new Integer(line.substring(line.indexOf("Content-Length:") + 16, line.length)).intValue
      }

      var requestPayload = ""
      // read the post data
      if (postDataStartingIndex > 0) {
        val charArray = new Array[Char](postDataStartingIndex)
        in.read(charArray, 0, postDataStartingIndex)
        requestPayload = new String(charArray)
      }

      println(requestPayload.getBytes.length)
      val response = new PrintWriter(clientConnection.getOutputStream)

      response.println("HTTP/1.0 200 OK")
      response.println("Content-Type: text/html")
      decrypt("src/main/resources/keypair_DER/private_key.der", DatatypeConverter.parseHexBinary(requestPayload)) match {
        case Right(x) => response.println(x)
        case Left(x) => response.println(x.getMessage)
      }

      response.flush()
      clientConnection.close()
    }
  }

}

object Server {

  def main(args: Array[String]): Unit = {

    new Server().start()
  }

  private def readPrivateKey(filename: String): Either[Throwable, PrivateKey] = {
    try {
      val keyFactory = KeyFactory.getInstance("RSA")

      val privateKeySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(filename)))

      Right(keyFactory.generatePrivate(privateKeySpec))
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def decrypt(privateKey: String, ciphertext: Array[Byte]): Either[Throwable, Array[Byte]] = {
    readPrivateKey(privateKey).map(primary => {
      val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
      cipher.init(Cipher.DECRYPT_MODE, primary)
      cipher.doFinal(ciphertext)
    })
  }

}
