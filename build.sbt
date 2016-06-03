name := "chisel-module-template"

version := "1.0"

val chiselVersion = System.getProperty("chiselVersion", "3.0")

scalaVersion := "2.11.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)
libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.0-BETA-SNAPSHOT"

libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.1-BETA-SNAPSHOT"


