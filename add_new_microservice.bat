REM Création de l'arborescence du microservice
mkdir %1-service
cd %1-service

REM Création du fichier pom.xml
(
    echo ^<?xml version="1.0" encoding="UTF-8"?^>
    echo ^<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    echo ^         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"^>
    echo     ^<modelVersion^>4.0.0^</modelVersion^>
    echo     ^<parent^>
    echo         ^<groupId^>org.springframework.boot^</groupId^>
    echo         ^<artifactId^>spring-boot-starter-parent^</artifactId^>
    echo         ^<version^>3.2.3^</version^>
    echo         ^<relativePath/^>
    echo     ^</parent^>
    echo     ^<groupId^>com.jeroka^</groupId^>
    echo     ^<artifactId^>%1-service^</artifactId^>
    echo     ^<version^>0.0.1-SNAPSHOT^</version^>
    echo     ^<name^>%1-service^</name^>
    echo     ^<description^>Microservice %1^</description^>
    echo     ^<properties^>
    echo         ^<java.version^>17^</java.version^>
    echo     ^</properties^>
    echo     ^<dependencies^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.boot^</groupId^>
    echo             ^<artifactId^>spring-boot-starter-web^</artifactId^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.boot^</groupId^>
    echo             ^<artifactId^>spring-boot-starter-data-jpa^</artifactId^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.boot^</groupId^>
    echo             ^<artifactId^>spring-boot-starter-validation^</artifactId^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.boot^</groupId^>
    echo             ^<artifactId^>spring-boot-starter-security^</artifactId^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springdoc^</groupId^>
    echo             ^<artifactId^>springdoc-openapi-starter-webmvc-ui^</artifactId^>
    echo             ^<version^>2.5.0^</version^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.postgresql^</groupId^>
    echo             ^<artifactId^>postgresql^</artifactId^>
    echo             ^<scope^>runtime^</scope^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>io.jsonwebtoken^</groupId^>
    echo             ^<artifactId^>jjwt-api^</artifactId^>
    echo             ^<version^>0.11.5^</version^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>io.jsonwebtoken^</groupId^>
    echo             ^<artifactId^>jjwt-impl^</artifactId^>
    echo             ^<version^>0.11.5^</version^>
    echo             ^<scope^>runtime^</scope^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>io.jsonwebtoken^</groupId^>
    echo             ^<artifactId^>jjwt-jackson^</artifactId^>
    echo             ^<version^>0.11.5^</version^>
    echo             ^<scope^>runtime^</scope^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.boot^</groupId^>
    echo             ^<artifactId^>spring-boot-starter-test^</artifactId^>
    echo             ^<scope^>test^</scope^>
    echo         ^</dependency^>
    echo         ^<dependency^>
    echo             ^<groupId^>org.springframework.security^</groupId^>
    echo             ^<artifactId^>spring-security-test^</artifactId^>
    echo             ^<scope^>test^</scope^>
    echo         ^</dependency^>
    echo     ^</dependencies^>
    echo     ^<build^>
    echo         ^<plugins^>
    echo             ^<plugin^>
    echo                 ^<groupId^>org.springframework.boot^</groupId^>
    echo                 ^<artifactId^>spring-boot-maven-plugin^</artifactId^>
    echo             ^</plugin^>
    echo         ^</plugins^>
    echo     ^</build^>
    echo ^</project^>
) > pom.xml

REM Création du Dockerfile
(
    echo # Build stage
    echo FROM maven:3.8.4-openjdk-17-slim AS build
    echo WORKDIR /app
    echo.
    echo # Configurer les paramètres Maven pour éviter l'erreur read-only file system
    echo COPY pom.xml .
    echo # Crée d'abord un répertoire .m2 local avec les droits d'écriture
    echo RUN mkdir -p /root/.m2/repository
    echo # Télécharger les dépendances dans une étape séparée pour mieux utiliser le cache
    echo RUN mvn dependency:go-offline -B
    echo.
    echo COPY src ./src
    echo RUN mvn clean package -DskipTests
    echo.
    echo # Run stage
    echo FROM openjdk:17-jdk-alpine
    echo WORKDIR /app
    echo COPY --from=build /app/target/*.jar app.jar
    echo EXPOSE 8084
    echo ENTRYPOINT ["java","-jar","/app/app.jar"]
) > Dockerfile

REM Création des dossiers sources
mkdir src
cd src
mkdir main
cd main
mkdir java resources
cd resources
REM Génération du fichier application.properties
(
    echo # Server Configuration
    echo server.port=${%1_SERVICE_PORT}
    echo.
    echo # Database Configuration
    echo spring.datasource.url=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
    echo spring.datasource.username=${POSTGRES_USER}
    echo spring.datasource.password=${POSTGRES_PASSWORD}
    echo spring.jpa.hibernate.ddl-auto=update
    echo spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    echo spring.jpa.show-sql=true
    echo.
    echo # JWT Configuration
    echo jwt.secret=${JWT_SECRET}
    echo jwt.expiration=${JWT_EXPIRATION}
    echo.
    echo # Logging Configuration
    echo logging.level.root=INFO
    echo logging.level.com.jeroka.subscription=DEBUG
    echo logging.pattern.console=%%d{yyyy-MM-dd HH:mm:ss} [%%thread] %%-5level %%logger{36} - %%msg%%n
) > application.properties


REM Génération du fichier docker-compose.yml
(
    echo %1-service:
    echo build: ./%1-service
    echo container_name: %1-service
    echo environment:
    echo SPRING_DATASOURCE_URL: jdbc:postgresql://jeroka_db:5432/${POSTGRES_DB}
    echo SPRING_DATASOURCE_USERNAME: ${POSTGRES_USER}
    echo SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
    echo JWT_SECRET: ${JWT_SECRET}
    echo JWT_EXPIRATION: ${JWT_EXPIRATION}
    echo SERVER_PORT: ${%1_SERVICE_PORT}
    echo depends_on:
    echo - jeroka_db
    echo ports:
    echo - "${%1_SERVICE_PORT}:${%1_SERVICE_PORT}"
    echo networks:
    echo - backend
    echo. 
    echo.
    echo ROUTE_%1_SERVICE: "${ROUTE_%1_SERVICE}"
) > docker-compose.yml

cd ..
cd java
mkdir com
cd com
mkdir jeroka
cd jeroka
mkdir %1
cd %1

REM Création du fichier principal
set SERVICE_NAME=%1
for %%A in (%SERVICE_NAME%) do set SERVICE_CLASS=%%~nA
for /f "tokens=1* delims=" %%A in ("%SERVICE_CLASS%") do set SERVICE_CLASS_UC=%%A
set SERVICE_CLASS_UC=%SERVICE_CLASS_UC:~0,1%%SERVICE_CLASS_UC:~1%
set FILE_NAME=%SERVICE_CLASS_UC%ServiceApplication.java
echo %FILE_NAME%
echo package com.jeroka.%1;> %FILE_NAME%
echo.>> %FILE_NAME%
echo import org.springframework.boot.SpringApplication;>> %FILE_NAME%
echo import org.springframework.boot.autoconfigure.SpringBootApplication;>> %FILE_NAME%
echo.>> %FILE_NAME%
echo @SpringBootApplication>> %FILE_NAME%
echo public class %SERVICE_CLASS_UC%ServiceApplication {>> %FILE_NAME%
echo.>> %FILE_NAME%
echo     public static void main(String[] args) {>> %FILE_NAME%
echo         SpringApplication.run(%SERVICE_CLASS_UC%ServiceApplication.class, args);>> %FILE_NAME%
echo     }>> %FILE_NAME%
echo }>> %FILE_NAME%

mkdir controller dto security services repository model
cd security
mkdir services
cd services

REM Création du fichier de configuration de sécurité
    echo package com.jeroka.%1.security.services;>> WebSecurityConfig.java
    echo.>> WebSecurityConfig.java
    echo import org.springframework.context.annotation.Bean;>> WebSecurityConfig.java
    echo import org.springframework.context.annotation.Configuration;>> WebSecurityConfig.java
    echo import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;>> WebSecurityConfig.java
    echo import org.springframework.security.config.annotation.web.builders.HttpSecurity;>> WebSecurityConfig.java
    echo import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;>> WebSecurityConfig.java
    echo import org.springframework.security.config.http.SessionCreationPolicy;>> WebSecurityConfig.java
    echo import org.springframework.security.web.SecurityFilterChain;>> WebSecurityConfig.java
    echo import org.springframework.web.cors.CorsConfiguration;>> WebSecurityConfig.java
    echo import org.springframework.web.cors.CorsConfigurationSource;>> WebSecurityConfig.java
    echo import org.springframework.web.cors.UrlBasedCorsConfigurationSource;>> WebSecurityConfig.java
    echo import java.util.Arrays;>> WebSecurityConfig.java
    echo.>> WebSecurityConfig.java
    echo @Configuration>> WebSecurityConfig.java
    echo @EnableWebSecurity>> WebSecurityConfig.java
    echo public class WebSecurityConfig ^{>> WebSecurityConfig.java
    echo.>> WebSecurityConfig.java
    echo     @Bean>> WebSecurityConfig.java
    echo     public SecurityFilterChain filterChain(HttpSecurity http^) throws Exception ^{>> WebSecurityConfig.java
    echo         http>> WebSecurityConfig.java
    echo             .cors(cors ^-^> cors.configurationSource(corsConfigurationSource(^)^)^)>> WebSecurityConfig.java
    echo             .csrf(csrf ^-^> csrf.disable(^)^)>> WebSecurityConfig.java
    echo             .sessionManagement(session ^-^> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS^)^)>> WebSecurityConfig.java
    echo             .authorizeHttpRequests(auth ^-^> >> WebSecurityConfig.java
    echo                 auth.requestMatchers("/api/**"^).permitAll(^)>> WebSecurityConfig.java
    echo                     .requestMatchers("/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**"^).permitAll(^)>> WebSecurityConfig.java
    echo                     .anyRequest(^).authenticated(^)>> WebSecurityConfig.java
    echo             ^)>> WebSecurityConfig.java
    echo             ;>> WebSecurityConfig.java
    echo             return http.build(^);>> WebSecurityConfig.java
    echo     ^}>> WebSecurityConfig.java
    echo.>> WebSecurityConfig.java
    echo     @Bean>> WebSecurityConfig.java
    echo     public CorsConfigurationSource corsConfigurationSource(^) ^{>> WebSecurityConfig.java
    echo         CorsConfiguration configuration = new CorsConfiguration(^);>> WebSecurityConfig.java
    echo         configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:8090"^));>> WebSecurityConfig.java
    echo         configuration.setAllowedMethods(Arrays.asList("*"^));>> WebSecurityConfig.java
    echo         configuration.setAllowedHeaders(Arrays.asList("*"^));>> WebSecurityConfig.java
    echo         configuration.setAllowCredentials(true^);>> WebSecurityConfig.java
    echo         configuration.setMaxAge(3600L^);>> WebSecurityConfig.java
    echo.>> WebSecurityConfig.java
    echo         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(^);>> WebSecurityConfig.java
    echo         source.registerCorsConfiguration("/**", configuration^);>> WebSecurityConfig.java
    echo         return source; >> WebSecurityConfig.java
    echo     ^} >> WebSecurityConfig.java
    echo ^} >> WebSecurityConfig.java


REM ajouter la route dans le .env et .env.docker
REM ajouter dans le docker-compose.yml
REM ajouter dans le api-gateway/src/main/java/com/jeroka/gateway/controller/AggregatedDocsController.java
REM ajouter dans le api-gateway/src/main/java/com/jeroka/gateway/controller/ProxyController.java
REM créer les fichiers controller, dto, security, services, repository, model et les mapper avec IA
REM ajouter dans init/01-schema.sql les tables avec IA
REM ajouter dans init/02-data.sql les données de test avec IA







