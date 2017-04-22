import sbt._
import Keys._

object ChiselDependencies {

  case class PackageProject(packageName: String, base: Option[File] = None, settings: Option[Seq[Def.Setting[_]]] = None)

  lazy val subProjectsSetting = settingKey[Seq[PackageProject]]("Subprojects to build")
}
