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

sudo mv $proj_home/attendance-provider/target/attendance-provider-0.0.1-SNAPSHOT.jar $proj_home/attendance-provider/target/demo.jar # 兼容所有sh脚本

docker run -d --restart=on-failure:5 --privileged=true \
       --name attendance  attendance \
       java \
       -Duser.timezone=Asia/Shanghai \
       -jar /home/demo.jar