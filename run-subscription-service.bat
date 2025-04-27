@echo off
echo ===================================
echo Lancement du service d'abonnements
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
echo Port configuré : %SUBSCRIPTION_SERVICE_PORT%
if not "%SUBSCRIPTION_SERVICE_PORT%"=="8084" (
    echo Attention : Le port n'est pas 8084 comme attendu !
    echo Port actuel : %SUBSCRIPTION_SERVICE_PORT%
)

set SERVER_PORT=%SUBSCRIPTION_SERVICE_PORT%
set JWT_SECRET=%JWT_SECRET%
set JWT_EXPIRATION=%JWT_EXPIRATION%

echo.
echo 5. Nettoyage du service...
cd subscription-service
call mvn clean

echo.
echo 6. Installation du service...
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation du service d'abonnements
    exit /b 1
)

echo.
echo 7. Lancement du service sur le port %SERVER_PORT%...
call mvn spring-boot:run
