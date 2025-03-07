FROM gradle:8-jdk17 AS builder

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
