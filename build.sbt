organization := "com.itrative"

name := "spray-slick-rest"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= {
  Seq(
    "com.github.finagle" %% "finch-core" % "0.5.0",
    "com.github.finagle" %% "finch-jackson" % "0.5.0",
    "com.typesafe.slick" %% "slick" % "2.1.0",
    "mysql" % "mysql-connector-java" % "5.1.34"
  )
}