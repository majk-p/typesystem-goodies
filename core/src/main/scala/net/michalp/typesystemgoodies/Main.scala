/*
 * Copyright 2022 mchalp.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.michalp.typesystemgoodies

import cats.data.NonEmptyList
import cats.effect.IO
import cats.effect.IOApp
import cats.effect._
import cats.implicits._
import eu.timepit.refined.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import sttp.client3._
import sttp.tapir.server.http4s.Http4sServerInterpreter

import scala.concurrent.ExecutionContext


object Main extends IOApp {

  val validateRoute = 
    OrderEndpoints.validate.serverLogic(_ => ().asRight[Unit].pure[IO])

  // converting an endpoint to a route (providing server-side logic); extension method comes from imported packages
  val helloWorldRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      validateRoute
    )

  val serverResource = BlazeServerBuilder[IO]
      .withExecutionContext(ExecutionContext.global)
      .bindHttp(8080, "localhost")
      .withHttpApp(Router("/" -> helloWorldRoutes).orNotFound)
      .resource
  
  val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
  val request = basicRequest.response(asStringAlways).post(uri"http://localhost:8080/validate")
  
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
  override def run(args: List[String]): IO[ExitCode] = {
    serverResource
      .use { _ =>
        verifyInvalidOrder *> verifyValidOrder
      }
      .as(ExitCode.Success)
  }
}
