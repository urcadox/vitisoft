name := """notification-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "org.postgresql" % "postgresql" % "9.4.1209"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
