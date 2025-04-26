@echo off
echo Cleaning and rebuilding Album Service with Plan Proxy...

cd album-service

echo Cleaning previous build...
call mvn clean

echo Removing target directory if it exists...
if exist target rmdir /s /q target

echo Building Album Service...
call mvn clean install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo Build failed with error code %ERRORLEVEL%
    exit /b %ERRORLEVEL%
)

echo Build completed successfully!
echo Starting service...
call mvn spring-boot:run 