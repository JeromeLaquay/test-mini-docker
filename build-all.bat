@echo off
echo Building all microservices...

echo.
echo Building API Gateway...
cd api-gateway
call mvn clean install
if errorlevel 1 (
    echo Error building API Gateway
    exit /b 1
)
cd ..

echo.
echo Building Auth Service...
cd auth-service
call mvn clean install
if errorlevel 1 (
    echo Error building Auth Service
    exit /b 1
)
cd ..

echo.
echo Building Album Service...
cd album-service
call mvn clean install
if errorlevel 1 (
    echo Error building Album Service
    exit /b 1
)
cd ..

echo.
echo Building Subscription Service...
cd subscription-service
call mvn clean install
if errorlevel 1 (
    echo Error building Subscription Service
    exit /b 1
)
cd ..

echo.
echo All microservices built successfully!
pause 