# Skeleton `sbt` code to support Chisel projects as subprojects.

Clone or merge files from this repo into your top-level Chisel project.

Edit project/chiselBuild.sbt to indicate the Chisel subprojects you wish to incorporate as source in your multi-project build.
Any required Chisel code that isn't built from source, will be downloaded via Ivy/Maven.

Merge code from build.sbt.example into your top-level build.sbt.

If you want BuildInfo available to each subproject, copy the top-level chiselBuildInfo.sbt into each subproject's root.

Ensure the project/chiselBuild directory is duplicated in each Chisel subproject.
