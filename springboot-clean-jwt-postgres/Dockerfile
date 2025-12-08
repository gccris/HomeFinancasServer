# Multi-stage build: build jar with Maven, run with OpenJDK
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
# copy source
COPY src ./src
# enable local maven wrapper if present
# build jar (skip tests for faster builds; remove -DskipTests for CI)
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# copy jar from builder (assumes standard maven target name)
ARG JAR_NAME=target/*.jar
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE ${APP_PORT:-8080}
ENTRYPOINT ["java","-jar","/app/app.jar"]