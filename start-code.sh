#!/usr/bin/env bash

git pull

m2_cache=~/.m2
proj_home=$PWD
img_mvn="maven:3.3.3-jdk-8"
img_output="attendance"

docker run --rm \
        -v $m2_cache:/root/.m2 \
        -v $proj_home:/usr/src/mymaven \
        -w /usr/src/mymaven $img_mvn mvn clean package -U

docker build -t $img_output .

docker rm -f attendance

docker run -d --restart=on-failure:5 --privileged=true \
       --name attendance  attendance \
       java \
       -Duser.timezone=Asia/Shanghai \
       -XX:+PrintGCDateStamps \
       -XX:+PrintGCTimeStamps \
       -XX:+PrintGCDetails \
       -XX:+HeapDumpOnOutOfMemoryError \
       -jar /home/demo.jar