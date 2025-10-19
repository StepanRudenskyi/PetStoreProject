FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/petstore-1.0-SNAPSHOT.jar /app/petstore-app.jar

RUN mkdir -p /app/uploads/images/products \
    && mkdir -p /app/uploads/images/categories
VOLUME ["/app/certs"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "petstore-app.jar"]