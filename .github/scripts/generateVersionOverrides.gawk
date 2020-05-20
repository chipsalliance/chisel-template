# Generate suitable definitions for sbt to override default dependency
#  specifications in the associated build.sbt, assuming the latter has
#  been structured to support this. It should contain something like:
#
# // Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
# val defaultVersions = Seq(
#   "chisel3" -> "3.3-SNAPSHOT",
#   "treadle" -> "1.2-SNAPSHOT"
# )
# 
# libraryDependencies ++= defaultVersions.map { case (dep, ver) =>
#   "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", ver) }

/#/ {
  # Strip comments
  gsub(/#.*$/,"")
}
/maven-version/ {
  # Print a series of "-DfooVersion=xxx" to override the default chisel versions in build.sbt
  printf "%s-D%sVersion=%s", sep, $2, $3; sep = " "
}
