FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build --no-daemon -x test

FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["-jar", "/app/app.jar"]
