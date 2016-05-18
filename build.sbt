name := "chisel-module-template"

version := "1.0"

val chiselVersion = System.getProperty("chiselVersion", "3.0")

resolvers += 
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % "3.0-BETA-SNAPSHOT",
  "edu.berkeley.cs" %% "chisel-iotesters" % "1.1-BETA-SNAPSHOT",
//  "edu.berkeley.cs" %% "chisel3" % chiselVersion,
//  "edu.berkeley.cs" %% "chisel-iotesters" % "1.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.4",
  "org.scalacheck" %% "scalacheck" % "1.12.4")

