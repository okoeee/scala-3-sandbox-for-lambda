val scala3Version = "3.5.1"

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-3-sandbox-for-lamda",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.3",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "3.14.0",
  )
