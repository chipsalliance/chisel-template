import chiselBuild.ChiselDependencies

name := "chisel-template"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

val chiselPackages: Seq[String] = Seq("chisel3", "chisel-iotesters")

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
val oldVersions: Map[String, String] = {
  val newVersions = {
    for (p <- chiselPackages; version = sys.props.getOrElse(p + "Version", ""); if version != "")
      yield ChiselDependencies.PackageVersion(p, version)
  }
  ChiselDependencies.setVersions(newVersions)
}

libraryDependencies ++= ChiselDependencies.chiselLibraryDependencies(chiselPackages)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.5",
  "org.scalacheck" %% "scalacheck" % "1.12.4")

lazy val chisel_template = (project in file(".")).dependsOn(ChiselDependencies.chiselProjectDependencies():_ *)
