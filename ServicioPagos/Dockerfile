# Usamos JDK 17
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el jar generado
COPY target/ServicioPagos-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Activamos perfil docker al iniciar
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker"]
