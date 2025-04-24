# Utiliser une image de base Node.js
FROM node:lts-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Installer les dépendances
RUN npm ci

# Copier le reste des fichiers de l'application
COPY . .

# Construire l'application pour la production
RUN npm run build

# Utiliser une image nginx pour servir l'application
FROM nginx:stable-alpine

# Copier les fichiers construits depuis l'étape précédente
COPY --from=0 /app/dist /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Démarrer nginx
CMD ["nginx", "-g", "daemon off;"] 