#!/bin/sh
./mvnw -N io.takari:maven:wrapper:0.5.6
exec ./mvnw "$@"