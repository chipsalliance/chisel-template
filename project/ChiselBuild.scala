import sbt._
import Keys._

object ChiselBuild extends Build {

  lazy val commonSettings = Seq (
    organization := "edu.berkeley.cs",
    scalaVersion := "2.11.8",

    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.sonatypeRepo("releases")
    ),

    javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
  )

  lazy val publishSettings = Seq (
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },

    publishTo <<= version { v: String =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) {
	Some("snapshots" at nexus + "content/repositories/snapshots")
      }
      else {
	Some("releases" at nexus + "service/local/staging/deploy/maven2")
      }
    }
  )

  lazy val chisel = (project in file("chisel3")).
    settings(commonSettings: _*).
    settings(publishSettings: _*).
    dependsOn(firrtl)

  lazy val chisel_testers = (project in file("chisel-testers")).
    settings(commonSettings: _*).
    settings(publishSettings: _*).
    dependsOn(chisel, firrtl, firrtl_interpreter)

  lazy val firrtl = (project in file("firrtl")).
    settings(commonSettings: _*).
    settings(publishSettings: _*)

  lazy val firrtl_interpreter = (project in file("firrtl-interpreter")).
    settings(commonSettings: _*).
    settings(publishSettings: _*).
    dependsOn(firrtl)

}
