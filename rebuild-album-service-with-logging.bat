@echo off
echo Cleaning and rebuilding Album Service with enhanced error handling...

cd album-service

echo Cleaning previous build...
call mvn clean

echo Removing target directory if it exists...
if exist target rmdir /s /q target

echo Building Album Service with debug logging...
call mvn clean install -DskipTests -X

if %ERRORLEVEL% NEQ 0 (
    echo Build failed with error code %ERRORLEVEL%
    exit /b %ERRORLEVEL%
)

echo Build completed successfully!
echo Starting service with debug logging...
call mvn spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.jeroka.album=DEBUG 