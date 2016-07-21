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
  "org.postgresql" % "postgresql" % "9.4.1209",
  "com.typesafe.play" % "anorm_2.11" % "2.5.0",
  "org.scalaz" % "scalaz-core_2.11" % "7.2.4",
  "org.postgis" % "postgis-main" % "1.3.3",
  "org.postgis" % "postgis-jdbc" % "1.3.3"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
