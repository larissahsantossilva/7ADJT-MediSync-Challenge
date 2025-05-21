FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /build
COPY . .
WORKDIR /build/notificacao-service

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine AS deploy
WORKDIR /app
COPY --from=build /build/notificacao-service/target/*.jar /app/medisync.jar

ENTRYPOINT ["java", "-jar", "medisync.jar"]
