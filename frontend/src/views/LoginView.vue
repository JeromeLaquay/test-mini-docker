<template>
  <div class="login min-h-screen bg-gradient-to-br from-purple-900 via-indigo-900 to-blue-900">
    <div class="min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div class="max-w-md w-full space-y-8 bg-white/10 backdrop-blur-md p-8 rounded-xl shadow-2xl border border-white/20">
        <div>
          <h2 class="mt-6 text-center text-3xl font-extrabold text-white flex items-center justify-center">
            <span class="mr-3">🎵</span>
            JerokaDJ
          </h2>
          <p class="mt-2 text-center text-sm text-white/70">
            Connectez-vous à votre compte
          </p>
        </div>
        <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
          <div class="rounded-md shadow-sm -space-y-px">
            <div>
              <label for="username" class="sr-only">Nom d'utilisateur</label>
              <input
                id="username"
                v-model="username"
                type="text"
                required
                class="appearance-none rounded-t-md relative block w-full px-3 py-2 border border-white/20 bg-black/40 placeholder-white/50 text-white focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
                placeholder="Nom d'utilisateur"
                :disabled="loading"
              />
            </div>
            <div>
              <label for="password" class="sr-only">Mot de passe</label>
              <input
                id="password"
                v-model="password"
                type="password"
                required
                class="appearance-none rounded-b-md relative block w-full px-3 py-2 border border-white/20 bg-black/40 placeholder-white/50 text-white focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
                placeholder="Mot de passe"
                :disabled="loading"
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 transition-colors duration-200"
              :disabled="loading"
            >
              <span class="absolute left-0 inset-y-0 flex items-center pl-3">
                <span class="mr-2">{{ loading ? '⏳' : '🔑' }}</span>
              </span>
              {{ loading ? 'Connexion en cours...' : 'Se connecter' }}
            </button>
          </div>

          <div v-if="error" class="mt-4 p-4 rounded-md bg-red-500/20 text-red-200 border border-red-500/30">
            {{ error }}
          </div>

          <div class="text-center mt-4">
            <p class="text-sm text-white/70">
              Pas encore de compte ?
              <router-link 
                to="/register" 
                class="font-medium text-purple-400 hover:text-purple-300 transition-colors duration-200"
              >
                Créer un compte
              </router-link>
            </p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      username: '',
      password: '',
      loading: false,
      error: null
    };
  },
  methods: {
    async handleLogin() {
      this.loading = true;
      this.error = null;

      try {
        console.log('Tentative de connexion avec:', { username: this.username }); // Debug

        const response = await this.$store.dispatch('auth/login', {
          username: this.username,
          password: this.password
        });

        console.log('Réponse connexion:', response); // Debug

        // Vérification immédiate
        const storedUser = localStorage.getItem('user');
        console.log('User stocké après connexion:', storedUser); // Debug

        if (!storedUser) {
          throw new Error('Utilisateur non stocké après connexion');
        }

        // Vérification du token
        const userData = JSON.parse(storedUser);
        if (!userData.token) {
          throw new Error('Token manquant dans les données utilisateur');
        }

        // Redirection
        const redirectPath = localStorage.getItem('redirectAfterLogin') || '/profile';
        localStorage.removeItem('redirectAfterLogin');
        this.$router.push(redirectPath);

      } catch (error) {
        console.error('Erreur lors de la connexion:', error);
        this.error = error.response?.data?.message || 'Erreur lors de la connexion';
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.login {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style> 