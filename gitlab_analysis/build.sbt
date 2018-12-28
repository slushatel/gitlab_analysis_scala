name := "gitlab_analysis"

version := "0.1"

scalaVersion := "2.12.8"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"

// https://mvnrepository.com/artifact/org.gitlab/java-gitlab-api
libraryDependencies += "org.gitlab" % "java-gitlab-api" % "4.1.0"
