val scala3Version = "3.7.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tst-cruise",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    scalacOptions += "-deprecation",

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
