// See README.md for license details.

def scalacOptionsVersion(scalaVersion: String): Seq[String] = {
  Seq() ++ {
    // If we're building with Scala > 2.11, enable the compile option
    //  switch to support our anonymous Bundle definitions:
    //  https://github.com/scala/bug/issues/10047
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, scalaMajor: Long)) if scalaMajor < 12 => Seq()
      case _ => Seq("-Xsource:2.11")
    }
  }
}

def javacOptionsVersion(scalaVersion: String): Seq[String] = {
  Seq() ++ {
    // Scala 2.12 requires Java 8. We continue to generate
    //  Java 7 compatible code for Scala 2.11
    //  for compatibility with old clients.
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, scalaMajor: Long)) if scalaMajor < 12 =>
        Seq("-source", "1.7", "-target", "1.7")
      case _ =>
        Seq("-source", "1.8", "-target", "1.8")
    }
  }
}

name := "chisel-module-template"

version := "3.2.999"

scalaVersion := "2.12.10"

crossScalaVersions := Seq("2.12.10", "2.11.12")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
val defaultVersions = Map(
  "chisel3" -> "3.2.2",
  "chisel-iotesters" -> "1.3.0",
  "chisel-testers2"  -> "0.1.2"
  )

libraryDependencies ++= Seq("chisel3", "chisel-iotesters", "chisel-testers2").map {
  dep: String => "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep)) }

scalacOptions ++= scalacOptionsVersion(scalaVersion.value)

javacOptions ++= javacOptionsVersion(scalaVersion.value)

envVars in Test := Map("MAKEFLAGS" -> "VM_PARALLEL_BUILDS=1")

// https://mvnrepository.com/artifact/org.ini4j/ini4j
libraryDependencies += "org.ini4j" % "ini4j" % "0.5.1"

libraryDependencies += "org.pegdown" % "pegdown" % "1.4.2" % "test"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// Used to parse JSON file for executable format
val circeVersion = "0.11.1"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)
// Recommendations from http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

// Dump running times to stdout so we can figure out which tests that are running for too long
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
// FIXME disable tests html output for now until we can figure out why it fails to build
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "reports/")

// Print all unit tests   --> sbt printTests
// Compile all unit tests --> sbt test:compile
// Run a single test      --> sbt "test:testOnly *TestClassName"

val printTests = taskKey[Unit]("something")

printTests := {
  val tests = (definedTests in Test).value
  tests map { t =>
    println(t.name)
  }
}
