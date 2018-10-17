#!/usr/bin/env bash
#./gradlew
#./build/libs/*-swarm.jar
#./mvnw
./mvnw -DskipTests
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar ./target/*-swarm.jar
