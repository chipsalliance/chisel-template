import sbt._
import Keys._

import ChiselDependencies._

object SubprojectGenerator {

  // The basic chisel dependencies.
  val basicDependencies = collection.immutable.Map[String, Seq[String]](
    "chisel3" -> Seq("firrtl"),
    "chisel_testers" -> Seq("firrtl", "firrtl_interpreter", "chisel3"),
    "firrtl" -> Seq(),
    "firrtl_interpreter" -> Seq("firrtl")
  )

  def generate(output: File, projects: Seq[PackageProject]): Seq[File] = {
    val subappsString = projects.map(_.packageName).mkString(",")
    val packageProjects: scala.collection.immutable.Set[String] = projects.map(_.packageName).toSet
    println("SubprojectGenerator.generate: " + subappsString)
    def projFromPackageProject(p: PackageProject) = {
      val clientSettings = p.settings.getOrElse(Seq())
      val id = p.packageName
      val base = p.base.getOrElse(file(id)).name
      val projectDependenciesString = chiselProjectDependencies(id).mkString(", ")
      s"""
        |    lazy val $id = (project in file(\"$base\")).settings(
        |      $clientSettings ++ ChiselBuild.commonSettings ++ ChiselBuild.publishSettings ++ Seq(
        |        libraryDependencies ++= chiselLibraryDependencies("$id")
        |      )
        |    ).dependsOn($projectDependenciesString)
        |""".stripMargin
    }

    // For a given chisel project, return a sequence of project references,
    //  suitable for use as an argument to dependsOn().
    def chiselProjectDependencies(name: String): Seq[String] = {
      basicDependencies(name).filter(dep => packageProjects.contains(dep))
    }

    val basicDependenciesString = basicDependencies.map { case (k, v) => s"""      ("$k" -> Seq(${v.map(e => "\"" + e + "\"").mkString(", ")}))"""}.mkString(",\n")
    val packageProjectsBuildString = projects.map(projFromPackageProject).mkString("\n")
    val packageProjectsInitString = projects.map( p => s"""      "${p.packageName}" -> ${p.packageName}""").mkString(",\n")
    val source = s"""
        |import sbt._
        |import Keys._
        |import ChiselBuild._
        |import Dependencies._
        |
        |trait Subprojects {
        |  this : Build =>
        |
        |    val basicDependencies = collection.immutable.Map[String, Seq[String]](
        |$basicDependenciesString
        |    )
        |$packageProjectsBuildString
        |
        |    lazy val packageProjects = scala.collection.mutable.Map[String, ProjectReference](
        |$packageProjectsInitString
        |    )
        |}
        |""".stripMargin

    val outputFile = output / "Subprojects.scala"

    println(outputFile.getAbsolutePath)

    IO.write(outputFile,source)

    Seq(outputFile)
  }

}

object build extends Build {

  lazy val root = project.in(file(".")).settings(
    sourceGenerators in Compile <+= (sourceManaged in Compile, subProjectsSetting in Compile) map { (out, subProjectsSetting) =>
      SubprojectGenerator.generate(out, subProjectsSetting)
    }
  )
}
