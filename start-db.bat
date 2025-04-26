@echo off
echo Starting PostgreSQL database with Docker Compose...

REM Check if Docker is running
docker info > nul 2>&1
if errorlevel 1 (
    echo Error: Docker is not running. Please start Docker Desktop and try again.
    pause
    exit /b 1
)

REM Start the database
echo.
echo Starting PostgreSQL container...
docker-compose -f docker-compose.bdd.yml up -d

if errorlevel 1 (
    echo Error starting the database container.
    pause
    exit /b 1
)

echo.
echo PostgreSQL database started successfully!
echo.
echo Database information:
echo - Container name: jeroka_db
echo - Port: 5432
echo - Username: postgres
echo - Password: jeroka
echo - Database: jeroka_db
echo.
echo To stop the database, run: docker-compose -f docker-compose.bdd.yml down
echo.
echo Press any key to exit...
pause > nul 