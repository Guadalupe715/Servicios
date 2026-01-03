# ----------------------------
# Stage 1: Build con Maven
# ----------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos el pom y el código fuente
COPY pom.xml .
COPY src ./src

# Construimos el JAR (sin tests para que sea más rápido)
RUN mvn clean package -DskipTests

# ----------------------------
# Stage 2: Contenedor final solo con JDK
# ----------------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el jar construido desde el stage anterior
COPY --from=build /app/target/ServicioPagos-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Activamos perfil docker al iniciar
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker"]
