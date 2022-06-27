package net.michalp.typesystemgoodies

import cats.effect._
import cats.implicits._

object OrderRoutes {
  val validate = OrderEndpoints.validate.serverLogic(_ => ().asRight[Unit].pure[IO])
}
