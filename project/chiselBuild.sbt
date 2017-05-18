import chiselBuild.ChiselDependencies._

// Share everything in the upper level project/chiselBuild directory between build and meta-build
unmanagedSourceDirectories in Compile += baseDirectory.value / "chiselBuild"

subProjectsSetting := Seq(
  PackageProject("firrtl"),
  PackageProject("firrtl-interpreter", Some(file("firrtl-interpreter"))),
  PackageProject("chisel3"),
  PackageProject("chisel-testers", Some(file("chisel-testers")))
)
