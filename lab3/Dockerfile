# Используем базовый образ с Java и средой выполнения Spring Boot
FROM openjdk:17-jdk-slim
FROM maven:3.9.6

# Устанавливаем рабочий каталог внутри контейнера
WORKDIR /app

# Копируем JAR-файл приложения в контейнер (предполагается, что JAR-файл находится в каталоге target/)
COPY football-portal-application football
