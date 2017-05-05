import sbt._
import chiselBuild.ChiselDependencies.{PackageProject, basicDependencies}

object SubprojectGenerator {

  def generate(output: File, projects: Seq[PackageProject]): Seq[File] = {
    val subappsString = projects.map(_.packageName).mkString(",")
    val packageProjects: scala.collection.immutable.Set[String] = projects.map(_.packageName).toSet

    // Given a Chisel project, generate the sbt code to define it as a project.
    def projFromPackageProject(p: PackageProject) = {
      val clientSettings = p.settings.getOrElse(Seq())
      val id = p.packageName
      val base = p.base.getOrElse(file(id)).name
      val projectDependenciesString = chiselProjectDependencies(id).mkString(", ")
      s"""
        |    lazy val $id = (project in file(\"$base\")).settings(
        |      $clientSettings ++ ChiselProjectBuild.commonSettings ++ ChiselProjectBuild.publishSettings ++ Seq(
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

    val packageProjectsBuildString = projects.map(projFromPackageProject).mkString("\n")
    val packageProjectsInitString = projects.map( p => s"""      "${p.packageName}" -> ${p.packageName}""").mkString(",\n")
    val source = s"""
        |import sbt._
        |import Keys._
        |import ChiselProjectBuild._
        |import chiselBuild.ChiselDependencies._
        |
        |trait Subprojects {
        |  this : Build =>
        |
        |$packageProjectsBuildString
        |
        |    packageProjects = scala.collection.mutable.Map[String, ProjectReference](
        |$packageProjectsInitString
        |    )
        |}
        |""".stripMargin

    val outputFile = output / "Subprojects.scala"

    IO.write(outputFile,source)

    Seq(outputFile)
  }

}
