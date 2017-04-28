package chiselBuild

import sbt._
import Keys._
import chiselBuild.ChiselDependencies._

object ChiselProjects {
  subProjectsSetting := Seq(
    PackageProject("firrtl"),
    PackageProject("firrtl_interpreter", Some(file("firrtl-interpreter"))),
    PackageProject("chisel3"),
    PackageProject("chisel_testers", Some(file("chisel-testers")))
  )

}
