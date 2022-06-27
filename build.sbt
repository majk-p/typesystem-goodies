// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "net.michalp"
ThisBuild / organizationName := "mchalp.net"
ThisBuild / startYear := Some(2022)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("majkp-p", "Michal Pawlik")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

val Scala213 = "2.13.8"
// ThisBuild / crossScalaVersions := Seq(Scala213, "3.1.1")
ThisBuild / scalaVersion := Scala213 // the default Scala

lazy val Versions = new {
  val cats = "2.8.0"
  val catsEffect = "3.3.12"
  val circe = "0.14.2"
  val tapir = "1.0.1"
  val http4s = "0.23.12"
}

lazy val root = project
  .in(file("core"))
  .settings(
    name := "typesystem-goodies",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % Versions.cats,
      "org.typelevel" %%% "cats-effect" % Versions.catsEffect,
      "io.circe" %% "circe-refined" % Versions.circe,
      "com.softwaremill.sttp.tapir" %% "tapir-core" % Versions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-refined" % Versions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-cats" % Versions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % Versions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % Versions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % Versions.tapir,
      "org.http4s" %% "http4s-blaze-server" % Versions.http4s,
      "org.scalameta" %%% "munit" % "0.7.29" % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7" % Test
    )
  )
