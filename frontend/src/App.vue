<template>
  <div id="app" class="min-h-screen bg-gradient-to-br from-gray-900 via-blue-900 to-indigo-900 flex flex-col">
    <nav v-if="!isAuthPage" class="bg-gray-900/80 backdrop-blur-sm shadow-lg border-b border-gray-700">
      <div class="container mx-auto px-4 py-3">
        <div class="flex justify-between items-center">
          <router-link to="/home" class="flex items-center space-x-3">
            <img src="@/assets/x-logo.png" alt="JeroKa Logo" class="w-12 h-12 object-contain" />
            <span class="text-white font-bold text-2xl">JeroKa</span>
          </router-link>
          
          <div class="flex items-center space-x-6">
            <router-link 
              to="/home" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
              active-class="text-blue-400"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
              </svg>
              <span>Accueil</span>
            </router-link>
            <router-link 
              to="/albums" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
              active-class="text-blue-400"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5z"/>
              </svg>
              <span>Albums</span>
            </router-link>
            
            <router-link 
              to="/subscriptions" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
              active-class="text-blue-400"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"/>
              </svg>
              <span>Abonnements</span>
            </router-link>
            
            <router-link v-if="currentUser"
              to="/profile" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
              active-class="text-blue-400"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
              </svg>
              <span>Profil</span>
            </router-link>
            
            <template v-if="isAdmin">
              <router-link 
                to="/admin" 
                class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
                active-class="text-blue-400"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                </svg>
                <span>Administration</span>
              </router-link>
              
              <router-link 
                to="/users" 
                class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
                active-class="text-blue-400"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"/>
                </svg>
                <span>Utilisateurs</span>
              </router-link>
              
              <router-link 
                to="/music-upload" 
                class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
                active-class="text-blue-400"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
                </svg>
                <span>Upload</span>
              </router-link>
            </template>
            
            <button  v-if="currentUser"
              @click="logOut" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
              </svg>
              <span>Déconnexion</span>
            </button>
          </div>
          
          <div v-if="!currentUser" class="flex items-center space-x-4">
            <router-link 
              to="/login" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"/>
              </svg>
              <span>Connexion</span>
            </router-link>
            
            <router-link 
              to="/register" 
              class="text-gray-300 hover:text-white transition-colors duration-200 flex items-center space-x-2"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"/>
              </svg>
              <span>Inscription</span>
            </router-link>
          </div>
        </div>
      </div>
    </nav>

    <main class="flex-grow">
      <router-view />
    </main>

    <footer v-if="!isAuthPage" class="bg-gray-900/80 backdrop-blur-sm border-t border-gray-700">
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
              <li>
                <router-link to="/home" class="text-gray-300 hover:text-white text-sm transition-colors flex items-center space-x-2">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>Accueil</span>
                </router-link>
              </li>
              <li>
                <router-link to="/subscriptions" class="text-gray-300 hover:text-white text-sm transition-colors flex items-center space-x-2">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>Abonnements</span>
                </router-link>
              </li>
              <li>
                <router-link to="/profile" class="text-gray-300 hover:text-white text-sm transition-colors flex items-center space-x-2">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>Mon profil</span>
                </router-link>
              </li>
              <li>
                <router-link to="/music-upload" class="text-gray-300 hover:text-white text-sm transition-colors flex items-center space-x-2">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>Upload de musique</span>
                </router-link>
              </li>
            </ul>
          </div>

          <!-- Contact -->
          <div>
            <h2 class="text-white text-lg font-semibold mb-4">Contact</h2>
            <ul class="space-y-2 text-sm">
              <li class="text-gray-300 flex items-center space-x-2">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                </svg>
                <a href="mailto:contact@jeroka.com" class="hover:text-white transition-colors">
                  contact@jeroka.com
                </a>
              </li>
              <li class="text-gray-300 flex items-center space-x-2">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                </svg>
                <a href="tel:+33123456789" class="hover:text-white transition-colors">
                  +33 1 23 45 67 89
                </a>
              </li>
              <li class="text-gray-300 flex items-center space-x-2">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                </svg>
                <span>13 rue des Fauvettes, 59470 Wormhout</span>
              </li>
              <li class="text-gray-300 flex items-center space-x-2">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064"/>
                </svg>
                <span>Région Hauts-de-France</span>
              </li>
            </ul>
          </div>

          <!-- Réseaux sociaux -->
          <div>
            <h2 class="text-white text-lg font-semibold mb-4">Suivez-nous</h2>
            <div class="flex space-x-4">
              <a href="https://facebook.com/jeroka" target="_blank" rel="noopener noreferrer" class="text-gray-300 hover:text-white transition-colors" aria-label="Facebook">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 2h-3a5 5 0 00-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 011-1h3z"/>
                </svg>
              </a>
              <a href="https://twitter.com/jeroka" target="_blank" rel="noopener noreferrer" class="text-gray-300 hover:text-white transition-colors" aria-label="Twitter">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2c9 5 20 0 20-11.5a4.5 4.5 0 00-.08-.83A7.72 7.72 0 0023 3z"/>
                </svg>
              </a>
              <a href="https://instagram.com/jeroka" target="_blank" rel="noopener noreferrer" class="text-gray-300 hover:text-white transition-colors" aria-label="Instagram">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11.37A4 4 0 1112.63 8 4 4 0 0116 11.37zm1.5-4.87h.01"/>
                  <rect stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x="3" y="3" width="18" height="18" rx="5"/>
                </svg>
              </a>
            </div>
          </div>
        </div>

        <!-- Barre de copyright -->
        <div class="mt-8 pt-8 border-t border-gray-700">
          <div class="flex flex-col md:flex-row justify-between items-center">
            <p class="text-gray-300 text-sm">
              © {{ new Date().getFullYear() }} JeroKa. Tous droits réservés.
            </p>
            <div class="flex space-x-4 mt-4 md:mt-0">
              <router-link to="/privacy" class="text-gray-300 hover:text-white text-sm transition-colors">
                Politique de confidentialité
              </router-link>
              <router-link to="/terms" class="text-gray-300 hover:text-white text-sm transition-colors">
                Conditions d'utilisation
              </router-link>
              <router-link to="/sitemap" class="text-gray-300 hover:text-white text-sm transition-colors">
                Plan du site
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    isAdmin() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN');
      }
      return false;
    },
    isAuthPage() {
      return ['/login', '/register'].includes(this.$route.path);
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  },
  watch: {
    $route(to) {
      const pageTitles = {
        '/': 'JeroKa - Plateforme de gestion musicale pour DJs',
        '/home': 'JeroKa - Accueil',
        '/profile': 'JeroKa - Mon profil',
        '/admin': 'JeroKa - Administration',
        '/users': 'JeroKa - Gestion des utilisateurs',
        '/music-upload': 'JeroKa - Upload de musique',
        '/login': 'JeroKa - Connexion',
        '/register': 'JeroKa - Inscription'
      };
      document.title = pageTitles[to.path] || 'JeroKa';

      const metaDescription = document.querySelector('meta[name="description"]');
      if (!metaDescription) {
        const meta = document.createElement('meta');
        meta.name = 'description';
        meta.content = 'JeroKa - Plateforme professionnelle de gestion musicale pour DJs. Gérez votre bibliothèque, partagez vos playlists et connectez-vous avec d\'autres artistes.';
        document.head.appendChild(meta);
      }
    }
  },
  mounted() {
    const metaTags = [
      {
        name: 'keywords',
        content: 'DJ, musique, gestion musicale, playlist, upload musique, streaming, artistes'
      },
      {
        property: 'og:title',
        content: 'JeroKa - Plateforme de gestion musicale pour DJs'
      },
      {
        property: 'og:description',
        content: 'Gérez votre bibliothèque musicale, partagez vos playlists et connectez-vous avec d\'autres artistes.'
      },
      {
        property: 'og:type',
        content: 'website'
      },
      {
        name: 'twitter:card',
        content: 'summary_large_image'
      }
    ];

    metaTags.forEach(tag => {
      const meta = document.createElement('meta');
      Object.keys(tag).forEach(key => {
        meta[key] = tag[key];
      });
      document.head.appendChild(meta);
    });
  }
};
</script>

<style>
body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background-color: #111827;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.05) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 40px 40px;
}

main {
  flex: 1;
}
</style> 