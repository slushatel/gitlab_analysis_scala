gradle shadowJar
docker cp gitlab-analysis-scala-1.0-SNAPSHOT-all.jar dockersparklivy_master_1:/alljars/
root@master:/usr/spark-2.2.1# ./bin/spark-submit --class GitlabLangCount --master spark://master:7077 --executor-memory 1G --total-executor-cores 10 /alljars/gitlab-analysis-scala-1.0-SNAPSHOT-all.jar

./bin/spark-submit --class GitlabLangCount --master local[2] --executor-memory 1G --total-executor-cores 10 --conf "spark.driver.userClassPathFirst=false" --conf "spark.driver.extraJavaOptions=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005" /alljars/gitlab-analysis-scala-1.0-SNAPSHOT-all.jar
