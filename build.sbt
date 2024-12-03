import Dependencies.{Libraries, Versions}

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / useCoursier  := false
ThisBuild / scalaVersion := Versions.scala
ThisBuild / organization := "com.pg.bigdata"

lazy val root = (project in file("."))
  .settings(
    name := "aoc2024",
    credentials ++= Seq(Credentials(Path.userHome / ".sbt" / ".credentials"), Credentials(Path.userHome / "credentials.txt")),
    resolvers ++= Seq("utils" at "https://pkgs.dev.azure.com/dh-platforms-devops/app-deng-nas_us/_packaging/com.pg.bigdata/maven/v1"),
    libraryDependencies ++= Seq(
      Libraries.aoc
    )
  )
