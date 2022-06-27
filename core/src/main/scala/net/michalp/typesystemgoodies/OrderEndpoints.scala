package net.michalp.typesystemgoodies


import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._

object OrderEndpoints {

  val validate: PublicEndpoint[Order, Unit, Unit, Any] =
    endpoint.post.in("validate").in(jsonBody[Order]).out(jsonBody[Unit])

}
