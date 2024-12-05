FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/petstore-1.0-SNAPSHOT.jar /app/petstore-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/petstore-app.jar"]