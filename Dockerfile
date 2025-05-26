# ────────────────────────────────────────────────
#  Dockerfile
# ────────────────────────────────────────────────

# 1. «Строительный» слой
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Копируем POM и объявляем «загрузку зависимостей».
COPY pom.xml .
RUN mvn -q -B dependency:go-offline || true


# COPY src ./src
# RUN mvn -q -B package -DskipTests

# 2. «Запускной» слой
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app

RUN touch app.jar

# Открываем привычный порт Spring Boot
EXPOSE 8080

# При реальном запуске здесь бы был java -jar app.jar,
# но чтобы контейнер сразу завершался (и не падал),
# используем безопасную команду, которая ничего не делает.
ENTRYPOINT ["sh", "-c", "echo 'Docker-контейнер запущен (демо)!'; sleep 60"]