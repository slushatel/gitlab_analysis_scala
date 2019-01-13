FROM gettyimages/spark:2.2.1-hadoop-2.8

# Livy
ARG LIVY_VERSION=0.4.0-incubating

RUN apt-get update \
 && apt-get install -y wget \
 && apt-get install -y vim \
 && apt-get install -y procps \
 && apt-get clean \
 && cd /usr && mkdir livy && cd livy && wget http://apache.cp.if.ua/incubator/livy/${LIVY_VERSION}/livy-${LIVY_VERSION}-bin.zip \
 && unzip livy-${LIVY_VERSION}-bin.zip
 
COPY ./docker-entrypoint.sh /
COPY ./livy.conf /usr/livy/livy-0.4.0-incubating-bin/conf
