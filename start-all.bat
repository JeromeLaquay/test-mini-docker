@echo off
echo Starting all microservices...

echo.
echo Starting Auth Service...
start "Auth Service" cmd /c "cd auth-service && mvn spring-boot:run"
timeout /t 30

echo.
echo Starting Album Service...
start "Album Service" cmd /c "cd album-service && mvn spring-boot:run"
timeout /t 30

echo.
echo Starting Subscription Service...
start "Subscription Service" cmd /c "cd subscription-service && mvn spring-boot:run"
timeout /t 30

echo.
echo Starting API Gateway...
start "API Gateway" cmd /c "cd api-gateway && mvn spring-boot:run"

echo.
echo All microservices are starting...
echo.
echo Services started in the following order:
echo 1. Auth Service (port 8081)
echo 2. Album Service (port 8083)
echo 3. Subscription Service (port 8084)
echo 4. API Gateway (port 8080)
echo.
echo Please wait for all services to initialize...
echo You can check the status in each command window.
echo.
echo Press any key to close all services when you're done...
pause > nul

echo.
echo Stopping all services...
taskkill /FI "WINDOWTITLE eq Auth Service*" /F
taskkill /FI "WINDOWTITLE eq Album Service*" /F
taskkill /FI "WINDOWTITLE eq Subscription Service*" /F
taskkill /FI "WINDOWTITLE eq API Gateway*" /F

echo All services stopped. 