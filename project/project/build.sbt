
//unmanagedSourceDirectories in Compile += baseDirectory.value / "chiselBuild"
unmanagedSourceDirectories in Compile += baseDirectory.value.getParentFile / "chiselBuild"
