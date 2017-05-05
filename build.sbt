// See LICENSE for license details.

import chiselBuild.ChiselDependencies._

site.settings

buildInfoUsePackageAsPath := true

  lazy val root = (project in file (".")).
    settings(commonSettings: _*).
    settings(
      publishLocal := {},
      publish := {},
      packagedArtifacts := Map.empty
    ).
    aggregate(packageProjectsMap.values.toSeq: _*)

publishArtifact in root := false

publish in root := {}
