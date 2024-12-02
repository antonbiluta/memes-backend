FROM openjdk:23-jdk-slim AS builder

ENV GRADLE_USER_HOME=/home/gradle/cache

WORKDIR /app

COPY --chmod=777 . /home/gradle/src
WORKDIR /home/gradle/src

RUN ./gradlew build --no-daemon

FROM openjdk:23-jdk-slim

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
