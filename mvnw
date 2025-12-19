#!/bin/sh
# Minimal mvnw shim: prefer system `mvn` to avoid broken wrapper recursion.
if command -v mvn >/dev/null 2>&1; then
	exec mvn "$@"
else
	echo "Maven not found. Install Maven or restore the Maven Wrapper (maven-wrapper.jar)."
	exit 1
fi