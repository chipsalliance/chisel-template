
unmanagedSourceDirectories in Compile += baseDirectory.value / "project"

lazy val dummySetting = settingKey[Int]("A dummy")

dummySetting in Compile := {
  println("sbd: " + (baseDirectory in Compile).value)
  println("sub: " + (unmanagedBase in Compile).value)
  println("sus: " + (unmanagedSourceDirectories in Compile).value.mkString(", "))
  0
}
