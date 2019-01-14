#!/usr/bin/env bash

cd $SPARK_HOME
bin/spark-class org.apache.spark.deploy.master.Master -h master &
sleep 10
/usr/livy/livy-0.4.0-incubating-bin/bin/livy-server start &



cleanup ()                                                                 
{                                                                          
  kill -s SIGTERM $!                                                         
  exit 0                                                                     
}                                                                          
                                                                           
trap cleanup SIGINT SIGTERM                                                
                                                                           
while [ 1 ]                                                                
do                                                                         
  sleep 60 &                                                             
  wait $!                                                                
done
