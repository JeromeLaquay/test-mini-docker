<template>
  <div id="app" class="min-h-screen bg-gradient-to-br from-gray-900 via-blue-900 to-indigo-900 flex flex-col z-0 relative isolate">
    <NavBar v-if="!isAuthPage" />
    <main class="flex-grow relative z-0 overflow-visible isolate">
      <router-view />
    </main>
    <FooterBar v-if="!isAuthPage" />
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
//import FooterBar from '@/components/FooterBar.vue';

export default {
  components: {
    NavBar,
    //FooterBar
  },
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