package net.michalp.typesystemgoodies


import cats.data.NonEmptyList
import cats.effect.IO
import eu.timepit.refined.auto._
import io.circe.syntax._
import sttp.client3._

object Checks {
  private val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
  private val request = basicRequest.response(asStringAlways).post(uri"http://localhost:8080/validate")

  val verifyInvalidOrder = IO {
    val result = request.body("{}").send(backend)
    assert(result.code.code == 400)
    println("Successfuly verified invaid order")
  }

  val verifyValidOrder = IO {
    val body = 
      Order(
        "17da8323-6e08-4519-aab6-ee0a9f9a30b3",
        NonEmptyList.of(
          OrderLine("product1", 10)
        )
      )
    val result = request.body(body.asJson.toString()).send(backend)
    assert(result.code.code == 200)
    println("Successfuly verified vaid order")
  }

}
