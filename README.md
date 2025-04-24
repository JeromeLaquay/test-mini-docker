# Microservices Example avec Docker

## Structure du projet

- `users-service/` : Microservice Java Spring Boot pour la gestion des utilisateurs
- `products-service/` : Microservice Java Spring Boot pour la gestion des produits
- `frontend/` : Application frontend Vue 3
- `nginx/` : Configuration du reverse proxy Nginx
- `docker-compose.yml` : Orchestration de tous les services

## Prérequis
- Docker
- Docker Compose

## Lancement du projet

1. Place-toi à la racine du projet
2. Lance la commande :
   ```sh
   docker-compose up --build
   ```
3. Accède à l'application sur [http://localhost](http://localhost)

## Accès aux services
- Frontend : [http://localhost](http://localhost)
- API Users : [http://localhost/api/users/](http://localhost/api/users/)
- API Products : [http://localhost/api/products/](http://localhost/api/products/)

## Notes
- Les microservices sont accessibles via Nginx (reverse proxy)
- La base de données PostgreSQL est accessible sur le port 5432

Adapte chaque service selon tes besoins spécifiques. 