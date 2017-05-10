// See LICENSE for license details.

import chiselBuild.{ChiselDependencies, ChiselSettings}

site.settings

lazy val root = (project in file (".")).
  settings(ChiselSettings.commonSettings: _*).
  settings(
    publishLocal := {},
    publish := {},
    packagedArtifacts := Map.empty
  ).
  aggregate(ChiselDependencies.packageProjectsMap.values.toSeq: _*)

publishArtifact in root := false

publish in root := {}
