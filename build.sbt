name := "gitlab_analysis"

version := "0.1"

scalaVersion := "2.12.8"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"

// https://mvnrepository.com/artifact/org.gitlab/java-gitlab-api
libraryDependencies += "org.gitlab" % "java-gitlab-api" % "4.1.0"

// https://mvnrepository.com/artifact/org.gitlab4j/gitlab4j-api
libraryDependencies += "org.gitlab4j" % "gitlab4j-api" % "4.9.11"
libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar"))

libraryDependencies += "org.apache.livy" % "livy-client-http" % "0.5.0-incubating"
libraryDependencies += "org.apache.livy" % "livy-scala-api_2.11" % "0.5.0-incubating"


