# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Configurer les paramètres Maven pour éviter l'erreur read-only file system
COPY pom.xml .
# Crée d'abord un répertoire .m2 local avec les droits d'écriture
RUN mkdir -p /root/.m2/repository
# Télécharger les dépendances dans une étape séparée pour mieux utiliser le cache
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"] 