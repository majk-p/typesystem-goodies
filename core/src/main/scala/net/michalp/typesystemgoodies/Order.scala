package net.michalp.typesystemgoodies

import cats.data.NonEmptyList
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string._
import io.circe.Codec
import io.circe.refined._

case class Order(orderId: String Refined Uuid, lines: NonEmptyList[OrderLine])

object Order {
  import io.circe.generic.semiauto._
  implicit val codec: Codec[Order] = deriveCodec
}
