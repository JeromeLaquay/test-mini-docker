<template>
  <footer class="bg-gray-900/80 backdrop-blur-sm border-t border-gray-700">
    <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
        <!-- À propos -->
        <div>
          <h2 class="text-white text-lg font-semibold mb-4">À propos de JeroKa</h2>
          <p class="text-gray-300 text-sm">
            Plateforme innovante de gestion musicale pour les DJs professionnels.
            Gérez votre bibliothèque musicale, partagez vos playlists et connectez-vous avec d'autres artistes.
          </p>
        </div>
        <!-- Liens rapides -->
        <div>
          <h2 class="text-white text-lg font-semibold mb-4">Liens rapides</h2>
          <ul class="space-y-2">
            <li><router-link to="/home" class="text-gray-300 hover:text-white text-sm">Accueil</router-link></li>
            <li><router-link to="/subscriptions" class="text-gray-300 hover:text-white text-sm">Abonnements</router-link></li>
            <li><router-link to="/profile" class="text-gray-300 hover:text-white text-sm">Mon profil</router-link></li>
            <li><router-link to="/music-upload" class="text-gray-300 hover:text-white text-sm">Upload de musique</router-link></li>
          </ul>
        </div>
        <!-- Contact -->
        <div>
          <h2 class="text-white text-lg font-semibold mb-4">Contact</h2>
          <ul class="space-y-2 text-sm">
            <li class="text-gray-300">contact@jeroka.com</li>
            <li class="text-gray-300">+33 1 23 45 67 89</li>
            <li class="text-gray-300">13 rue des Fauvettes, 59470 Wormhout</li>
            <li class="text-gray-300">Région Hauts-de-France</li>
          </ul>
        </div>
        <!-- Réseaux sociaux -->
        <div>
          <h2 class="text-white text-lg font-semibold mb-4">Suivez-nous</h2>
          <div class="flex space-x-4">
            <a href="https://facebook.com/jeroka" target="_blank" class="text-gray-300 hover:text-white">Facebook</a>
            <a href="https://twitter.com/jeroka" target="_blank" class="text-gray-300 hover:text-white">Twitter</a>
            <a href="https://instagram.com/jeroka" target="_blank" class="text-gray-300 hover:text-white">Instagram</a>
          </div>
        </div>
      </div>
      <div class="mt-8 pt-8 border-t border-gray-700 flex flex-col md:flex-row justify-between items-center">
        <p class="text-gray-300 text-sm">
          © {{ new Date().getFullYear() }} JeroKa. Tous droits réservés.
        </p>
        <div class="flex space-x-4 mt-4 md:mt-0">
          <router-link to="/privacy" class="text-gray-300 hover:text-white text-sm">Politique de confidentialité</router-link>
          <router-link to="/terms" class="text-gray-300 hover:text-white text-sm">Conditions d'utilisation</router-link>
          <router-link to="/sitemap" class="text-gray-300 hover:text-white text-sm">Plan du site</router-link>
        </div>
      </div>
    </div>
  </footer>
</template>
