name := """coop-edit"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "angularjs" % "1.3.0-beta.18",
  "org.webjars" % "requirejs" % "2.1.14-1"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)