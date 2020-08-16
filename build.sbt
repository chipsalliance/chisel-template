// See README.md for license details.

ThisBuild / organization := "edu.berkeley.cs"
ThisBuild / scalaVersion := "2.12.12"
ThisBuild / version      := "3.3-SNAPSHOT"

lazy val chiselTemplate = (project in file("."))
  .settings(
    name := "chisel-module-template",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.3.+",
      "edu.berkeley.cs" %% "chisel-iotesters" % "1.4.+" % "test",
      "edu.berkeley.cs" %% "chiseltest" % "0.2.2" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.11",
      "-deprecation",
      "-feature",
      "-language:reflectiveCalls",
      "-Xcheckinit"
    )
  )

