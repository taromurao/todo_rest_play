name := """todos"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

routesGenerator := InjectedRoutesGenerator

libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.3"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "1.8.5" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
