# Skeleton *sbt* code to support Chisel projects as subprojects.

Clone or merge files from this repo into your top-level Chisel project.

Merge code from build.sbt.example into your top-level build.sbt editing the `val chiselDeps = chisel.dependencies` definition to include those subprojects you wish to incorporate from source in your multi-project build.
Any required Chisel code that isn't built from source, will be downloaded via Ivy/Maven.

If you want BuildInfo available to each subproject, add `enablePlugins(BuildInforPlugin)` to each subproject's definition.

