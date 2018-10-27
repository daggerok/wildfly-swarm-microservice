#!/usr/bin/env bash
./gradlew
./build/libs/*-swarm.jar -Djava.net.preferIPv4Stack=true -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
##./mvnw
#./mvnw -DskipTests
#java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 ./target/*-swarm.jar -Djava.net.preferIPv4Stack=true
