name := "enc-dec-scala"

version := "0.1"

scalaVersion := "2.13.0-M2"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.4",
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP10"
)

libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.1" // for java9+

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.584"

//resolvers += "libs-releases" at "https://code.duwamish.com/artifactory/libs-release-local"
//resolvers += "libs-snapshots" at "https://code.duwamish.com/artifactory/libs-snapshot-local"
