call gradlew shadowJar

docker cp build\libs\gitlab-analysis-scala-1.0-SNAPSHOT-all.jar dockersparklivy_master_1:/

docker exec -it dockersparklivy_master_1 bash -c "/usr/spark-2.2.1/bin/spark-submit --class GitlabLangCount --master spark://master:7077 --executor-memory 1G --total-executor-cores 10 /gitlab-analysis-scala-1.0-SNAPSHOT-all.jar"
