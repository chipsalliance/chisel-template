import chiselBuild.ChiselDependencies._

unmanagedSourceDirectories in Compile += baseDirectory.value / "chiselBuild"
lazy val dummySetting = settingKey[Int]("dummy key")

subProjectsSetting := Seq(
  PackageProject("firrtl"),
  PackageProject("firrtl_interpreter", Some(file("firrtl-interpreter"))),
  PackageProject("chisel3"),
  PackageProject("chisel_testers", Some(file("chisel-testers")))
)
