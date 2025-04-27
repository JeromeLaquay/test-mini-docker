@echo off
echo ===================================
echo Chargement des variables d'environnement
echo ===================================

echo.
echo 1. Vérification du fichier .env...
if not exist .env (
    echo Erreur : Le fichier .env n'existe pas !
    exit /b 1
)

echo.
echo 2. Chargement des variables...
for /f "tokens=*" %%a in ('type .env ^| findstr /v "^#"') do (
    set %%a
)

echo.
echo 3. Configuration des variables Spring Boot...
set SPRING_DATASOURCE_URL=jdbc:postgresql://%POSTGRES_HOST%:%POSTGRES_PORT%/%POSTGRES_DB%
set SPRING_DATASOURCE_USERNAME=%POSTGRES_USER%
set SPRING_DATASOURCE_PASSWORD=%POSTGRES_PASSWORD%

echo.
echo 4. Vérification des variables...
echo.
echo === Base de données ===
echo POSTGRES_HOST: %POSTGRES_HOST%
echo POSTGRES_PORT: %POSTGRES_PORT%
echo POSTGRES_DB: %POSTGRES_DB%
echo POSTGRES_USER: %POSTGRES_USER%
echo POSTGRES_PASSWORD: %POSTGRES_PASSWORD%

echo.
echo === JWT ===
echo JWT_SECRET: %JWT_SECRET%
echo JWT_EXPIRATION: %JWT_EXPIRATION%

echo.
echo === Ports ===
echo AUTH_SERVICE_PORT: %AUTH_SERVICE_PORT%
echo ALBUM_SERVICE_PORT: %ALBUM_SERVICE_PORT%
echo SUBSCRIPTION_SERVICE_PORT: %SUBSCRIPTION_SERVICE_PORT%
echo API_GATEWAY_PORT: %API_GATEWAY_PORT%
echo FRONTEND_PORT: %FRONTEND_PORT%
echo NGINX_PORT: %NGINX_PORT%

echo.
echo === CORS ===
echo CORS_ALLOWED_ORIGINS: %CORS_ALLOWED_ORIGINS%
echo CORS_ALLOWED_METHODS: %CORS_ALLOWED_METHODS%
echo CORS_ALLOWED_HEADERS: %CORS_ALLOWED_HEADERS%
echo CORS_ALLOW_CREDENTIALS: %CORS_ALLOW_CREDENTIALS%
echo CORS_MAX_AGE: %CORS_MAX_AGE%

echo.
echo === Routes ===
echo ROUTE_SUBSCRIPTION_SERVICE: %ROUTE_SUBSCRIPTION_SERVICE%
echo ROUTE_AUTH_SERVICE: %ROUTE_AUTH_SERVICE%
echo ROUTE_ALBUM_SERVICE: %ROUTE_ALBUM_SERVICE%

echo.
echo === Spring Boot ===
echo SPRING_DATASOURCE_URL: %SPRING_DATASOURCE_URL%
echo SPRING_DATASOURCE_USERNAME: %SPRING_DATASOURCE_USERNAME%
echo SPRING_DATASOURCE_PASSWORD: %SPRING_DATASOURCE_PASSWORD%

echo.
echo ===================================
echo Variables d'environnement chargées !
echo ===================================
echo.
echo Pour utiliser ces variables dans un autre script :
echo call set-env.bat
echo.


