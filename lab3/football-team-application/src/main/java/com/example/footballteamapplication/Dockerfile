# Используем базовый образ с Java и средой выполнения Spring Boot
FROM openjdk:17-jdk-slim

# Устанавливаем рабочий каталог внутри контейнера
WORKDIR /app

# Копируем JAR-файл приложения в контейнер (предполагается, что JAR-файл находится в каталоге target/)
COPY target/football-team-application-0.0.1-SNAPSHOT.jar football-team-application.jar

# Экспонируем порт, на котором будет работать приложение
EXPOSE 8090

# Запускаем приложение при запуске контейнера
CMD ["java", "-jar", "football-team-application.jar"]
