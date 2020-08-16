// See README.md for license details.

// Common properties shared for a whole build
ThisBuild / scalaVersion := "2.12.12"
// These settings are mainly for publishing, they should be customized by the user
ThisBuild / organization := "edu.berkeley.cs"
ThisBuild / version      := "3.3-SNAPSHOT" // SNAPSHOT is used to indicate development

// A project is a compilation unit
// Many projects are fine with just one (like this example), but complex builds may have multiple
// The properties scoped in "ThisBuild" above are shared between all projects
lazy val chiselTemplate = (project in file("."))
  .settings(
    name := "chisel-module-template",
    libraryDependencies ++= Seq(
      // Fundamentally, Chisel is just a Scala library, so we depend on it as a library
      // Chisel 3 is versioning is of the style "3.<major>.<minor>"
      // Chisel maintains binary compatibility between minor versions
      // We use '+' to pull newest minor version
      "edu.berkeley.cs" %% "chisel3" % "3.3.+",
      // By default, dependencies are in the "main" scope, projects also have a "test" scope
      // main code lives in "src/main/", test is found in "src/test/"
      // The following depenencies will only be available in "src/test/"
      "edu.berkeley.cs" %% "chisel-iotesters" % "1.4.+" % "test",
      // Leading with a 0 indicates less stability so it's generally good to pin a specific version,
      // It's also reasonable to pin specific versions for the above dependencies as well
      "edu.berkeley.cs" %% "chiseltest" % "0.2.2" % "test"
    ),
    scalacOptions ++= Seq(
      // Required options for Chisel code
      "-Xsource:2.11",
      // Recommended options
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit"
    )
  )

