import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "3.7.3"

lazy val root = (project in file("."))
    .settings(
        name := "advent-of-code-2023",
        libraryDependencies ++= Seq(
            "com.lihaoyi" %% "fansi" % "0.5.0",
            "org.scalactic"  %% "scalactic"  % "3.2.18",
            "org.scalatest" %% "scalatest" % "3.2.18" % "test"
        )

    )
