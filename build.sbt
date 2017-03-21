// See LICENSE for license details.
import Dependencies._

site.settings

buildInfoUsePackageAsPath := true

  lazy val root = (project in file (".")).
    settings(commonSettings: _*).
    settings(
      publishLocal := {},
      publish := {},
      packagedArtifacts := Map.empty
    ).
    dependsOn(firrtl).
    aggregate(firrtl, chisel, firrtl_interpreter, chisel_testers)

publishArtifact in root := false

publish in root := {}
