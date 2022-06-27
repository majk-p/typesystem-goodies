package net.michalp.typesystemgoodies


import cats.effect.IO
import eu.timepit.refined.auto._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import sttp.tapir.server.http4s.Http4sServerInterpreter

import scala.concurrent.ExecutionContext

object Server {

  private val routes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      OrderRoutes.validate
    )

  val resource = BlazeServerBuilder[IO]
    .withExecutionContext(ExecutionContext.global)
    .bindHttp(8080, "localhost")
    .withHttpApp(Router("/" -> routes).orNotFound)
    .resource
}
