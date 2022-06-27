package net.michalp.typesystemgoodies

import eu.timepit.refined.auto._
import eu.timepit.refined.types.string._
import eu.timepit.refined.types.numeric._
import io.circe.generic.semiauto._
import io.circe.Codec
import io.circe.refined._

case class OrderLine(product: NonEmptyString, quantity: PosInt)

object OrderLine {
  OrderLine("123", 10) // Returns OrderLine
  // OrderLine("", 10)     // Doesn't compile

  implicit val codec: Codec[OrderLine] = deriveCodec
}
