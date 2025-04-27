@echo off
echo ===================================
echo Nettoyage et installation des services
echo ===================================

echo.
echo 1. Nettoyage des services...
cd api-gateway
call mvn clean
cd ..

cd auth-service
call mvn clean
cd ..

cd album-service
call mvn clean
cd ..

cd subscription-service
call mvn clean
cd ..

echo.
echo 2. Installation des services...
cd api-gateway
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation de api-gateway
    exit /b 1
)
cd ..

cd auth-service
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation de auth-service
    exit /b 1
)
cd ..

cd album-service
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation de album-service
    exit /b 1
)
cd ..

cd subscription-service
call mvn install -DskipTests
if errorlevel 1 (
    echo Erreur lors de l'installation de subscription-service
    exit /b 1
)
cd ..

