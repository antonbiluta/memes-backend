FROM harbor.biluta.ru/base-images/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build --no-daemon

FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["-jar", "/app/app.jar"]
