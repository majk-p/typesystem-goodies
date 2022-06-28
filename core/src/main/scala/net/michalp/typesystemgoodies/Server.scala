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

import cats.effect.IO
import eu.timepit.refined.auto._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import sttp.tapir._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import scala.concurrent.ExecutionContext

object Server {

  val myEndpoints: List[AnyEndpoint] = List(OrderEndpoints.validate)
  val swaggerEndpoints = SwaggerInterpreter().fromEndpoints[IO](myEndpoints, "My App", "1.0")

  private val routes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      List(
        OrderRoutes.validate,
      ) ++ swaggerEndpoints
    )

  val resource = BlazeServerBuilder[IO]
    .withExecutionContext(ExecutionContext.global)
    .bindHttp(8080, "localhost")
    .withHttpApp(Router("/" -> routes).orNotFound)
    .resource

}
