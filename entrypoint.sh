#!/bin/bash

set -m
set -x

export JAVA_OPTS="--server.port=${PORT0}"

java -jar micro-ci-test.jar $JAVA_OPTS
