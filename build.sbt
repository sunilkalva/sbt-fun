name := "hello"

version := "1.0"

scalaVersion := "2.11.2"

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")

libraryDependencies += "org.specs2" % "specs2_2.10" % "1.14" % "test"

def PreownedKittenProject(name: String): Project = (
  Project(name, file(name))
    settings(
    version := "1.0",
    organization := "com.test",
    libraryDependencies += "org.specs2" % "specs2_2.10" % "1.14" % "test"
    )
  )

//gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head
//val gitHeadCommitSha ="1.0"

lazy val common = (
  PreownedKittenProject("common")
    settings(
    makeVersionProperties := {
      val propFile = (resourceManaged in Compile).value / "version.properties"
      val content = "version=%s" format gitHeadCommitSha.value
      IO.write(propFile, content)
      Seq(propFile)
    },
    resourceGenerators in Compile <+= makeVersionProperties
    )
  )

lazy val analytics = (
  PreownedKittenProject("analytics")
    dependsOn common
    settings()
  )

lazy val website = (
  PreownedKittenProject("website")
    dependsOn common
    settings()
  )

libraryDependencies += "joda-time" % "joda-time" % "1.6.2"