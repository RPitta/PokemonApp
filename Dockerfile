#FROM maven:3.8.7-openjdk-18-slim AS build
#RUN mkdir -p workspace
#WORKDIR workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#COPY frontend /workspace/frontend
#RUN mvn -f pom.xml clean install -DskipTests=true
#
#FROM openjdk:17-jdk-alpine
#COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]

FROM openjdk:17-jdk-alpine as build
WORKDIR /app

# Copy [host local machine] to [working directory] inside image
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src src

# Package application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Minimal docker image to with command to run the app
FROM openjdk:17-jdk-alpine

ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*",  "me.Rodrigo.PokemonApp.boot.PokemonAppApplication"]