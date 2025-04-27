@echo off
echo ===================================
echo Lancement du service d'API Gateway
echo ===================================

echo.
echo 1. Vérification du fichier .env...
if not exist .env (
    echo Erreur : Le fichier .env n'existe pas !
    exit /b 1
)

echo.
echo 2. Chargement des variables d'environnement...
for /f "tokens=*" %%a in ('type .env ^| findstr /v "^#"') do (
    set %%a
)

echo.
echo 3. Configuration des variables Spring Boot...
set SPRING_DATASOURCE_URL=jdbc:postgresql://%POSTGRES_HOST%:%POSTGRES_PORT%/%POSTGRES_DB%
set SPRING_DATASOURCE_USERNAME=%POSTGRES_USER%
set SPRING_DATASOURCE_PASSWORD=%POSTGRES_PASSWORD%

echo.
echo 4. Vérification du port...
echo Port configuré : %API_GATEWAY_PORT%
if not "%API_GATEWAY_PORT%"=="8082" (
    echo Attention : Le port n'est pas 8082 comme attendu !
    echo Port actuel : %API_GATEWAY_PORT%
)

set SERVER_PORT=%API_GATEWAY_PORT%
set JWT_SECRET=%JWT_SECRET%
set JWT_EXPIRATION=%JWT_EXPIRATION%

echo.
echo 5. Nettoyage du service...
cd api-gateway
call mvn clean

echo.
echo 6. Installation du service...
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation du service d'API Gateway
    exit /b 1
)

echo.
echo 7. Lancement du service sur le port %SERVER_PORT%...
call mvn spring-boot:run
