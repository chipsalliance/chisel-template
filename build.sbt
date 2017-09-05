name := "chisel-module-template"

version := "1.1-SNAPSHOT"

scalaVersion := "2.11.11"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
val defaultVersions = Map(
  "chisel3" -> "3.1-SNAPSHOT",
  "chisel-iotesters" -> "1.2-SNAPSHOT",
  "firrtl" -> "1.1-SNAPSHOT",
  "firrtl-interpreter" -> "1.1-SNAPSHOT"
  )

def depVersion(dep: String): ModuleID = {
  "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep))
}

// The Chisel projects we know we'll require.
// This could be any (or all) of the BIG4 projects
val chiselDeps = chisel.dependencies(Seq(
    (depVersion("firrtl"), "firrtl"),
    (depVersion("firrtl-interpreter"), "firrtl-interpreter"),
    (depVersion("chisel3"), "chisel3"),
    (depVersion("chisel-iotesters"), "chisel-testers")
))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "org.scalacheck" %% "scalacheck" % "1.13.4") ++ chiselDeps.libraries

lazy val skeleton = (project in file("."))
  .dependsOn(chiselDeps.projects.map(classpathDependency(_)): _*)
