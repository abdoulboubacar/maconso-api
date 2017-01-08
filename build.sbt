name := """energie-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, DebianPlugin)

maintainer in Linux := "Abdoul Boubacar <arbmainassara@gmail.com>"

packageSummary in Linux := "API REST Energie"

packageDescription := "API REST Energie"


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "postgresql" % "postgresql" % "9.1-903.jdbc4",
  "io.swagger" %% "swagger-play2" % "1.5.3"
)
