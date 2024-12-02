FROM eclipse-temurin:21-jdk-alpine AS builder

ENV GRADLE_USER_HOME=/home/gradle/cache

WORKDIR /app

COPY . .

RUN ./gradlew clean build --no-daemon

FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

RUN ls -la /app/build/libs/

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["-jar", "/app/app.jar"]
