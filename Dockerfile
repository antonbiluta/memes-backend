# Используем образ OpenJDK для сборки приложения
FROM openjdk:21-jdk-alpine AS builder

# Устанавливаем переменную среды для Gradle
ENV GRADLE_USER_HOME=/home/gradle/cache

# Копируем исходный код приложения в образ
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Собираем приложение с помощью Gradle
RUN ./gradlew build --no-daemon

# Используем образ OpenJDK для запуска приложения
FROM adoptopenjdk/openjdk11:alpine-jre

# Копируем собранный JAR-файл из предыдущего образа в текущий образ
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

# Устанавливаем переменную среды для порта, на котором будет работать приложение
ENV PORT=8080

# Выполняем команду для запуска приложения
CMD ["java", "-jar", "/app/app.jar", "--server.port=${PORT}"]
