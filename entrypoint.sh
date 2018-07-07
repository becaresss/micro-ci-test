#!/bin/bash

set -m
set -x

export JAVA_OPTS="--server.port=${PORT0}"

java -jar ${project.artifactId}.jar $JAVA_OPTS
