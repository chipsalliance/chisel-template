import sbt._
import Keys._
import chiselBuild.ChiselDependencies.{subProjectsSetting, _}

object SubprojectGenerator {

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
      println(s"SubprojectGenerator.generate.chiselProjectDependencies: $name")
      basicDependencies(name).filter(dep => packageProjects.contains(dep))
    }

    val packageProjectsBuildString = projects.map(projFromPackageProject).mkString("\n")
    val packageProjectsInitString = projects.map( p => s"""      "${p.packageName}" -> ${p.packageName}""").mkString(",\n")
    val source = s"""
        |import sbt._
        |import Keys._
        |import ChiselBuild._
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

    println(outputFile.getAbsolutePath)

    IO.write(outputFile,source)

    Seq(outputFile)
  }

}

object build extends Build {

  lazy val dummySetting2 = settingKey[Int]("dummy key")
  dummySetting2 := {
    println("in SubprojectGenerator")
    println("subProjectsSetting: " + subProjectsSetting.value)
    2
  }

  lazy val root = project.in(file(".")).settings(
    sourceGenerators in Compile <+= (sourceManaged in Compile, subProjectsSetting) map { (out, projectsSetting) =>
      SubprojectGenerator.generate(out, projectsSetting)
    }
  )
}
