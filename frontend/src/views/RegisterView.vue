<template>
  <div class="register min-h-screen bg-gradient-to-br from-purple-900 via-indigo-900 to-blue-900">
    <div class="min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div class="max-w-md w-full space-y-8 bg-white/10 backdrop-blur-md p-8 rounded-xl shadow-2xl border border-white/20">
        <div>
          <h2 class="mt-6 text-center text-3xl font-extrabold text-white flex items-center justify-center">
            <span class="mr-3">🎵</span>
            JerokaDJ
          </h2>
          <p class="mt-2 text-center text-sm text-white/70">
            Créez votre compte
          </p>
        </div>
        <form class="mt-8 space-y-6" @submit.prevent="handleRegister">
          <div class="rounded-md shadow-sm space-y-4">
            <div>
              <label for="username" class="block text-sm font-medium text-white/90">Nom d'utilisateur</label>
              <input
                id="username"
                v-model="user.username"
                type="text"
                required
                class="appearance-none relative block w-full px-3 py-2 border border-white/20 bg-black/40 placeholder-white/50 text-white rounded-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
                placeholder="Nom d'utilisateur"
              />
            </div>
            <div>
              <label for="email" class="block text-sm font-medium text-white/90">Email</label>
              <input
                id="email"
                v-model="user.email"
                type="email"
                required
                class="appearance-none relative block w-full px-3 py-2 border border-white/20 bg-black/40 placeholder-white/50 text-white rounded-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
                placeholder="Email"
              />
            </div>
            <div>
              <label for="password" class="block text-sm font-medium text-white/90">Mot de passe</label>
              <input
                id="password"
                v-model="user.password"
                type="password"
                required
                class="appearance-none relative block w-full px-3 py-2 border border-white/20 bg-black/40 placeholder-white/50 text-white rounded-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
                placeholder="Mot de passe"
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
                <span class="mr-2">{{ loading ? '⏳' : '✨' }}</span>
              </span>
              {{ loading ? 'Inscription en cours...' : 'S\'inscrire' }}
            </button>
          </div>

          <div v-if="message" class="mt-4 p-4 rounded-md" :class="successful ? 'bg-green-500/20 text-green-200 border border-green-500/30' : 'bg-red-500/20 text-red-200 border border-red-500/30'">
            {{ message }}
          </div>

          <div class="text-center mt-4">
            <p class="text-sm text-white/70">
              Déjà un compte ?
              <router-link 
                to="/login" 
                class="font-medium text-purple-400 hover:text-purple-300 transition-colors duration-200"
              >
                Se connecter
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
  name: 'RegisterView',
  data() {
    return {
      user: {
        username: '',
        email: '',
        password: ''
      },
      loading: false,
      message: '',
      successful: false
    };
  },
  methods: {
    handleRegister() {
      this.loading = true;
      this.message = '';
      this.$store.dispatch('auth/register', this.user).then(
        () => {
          this.successful = true;
          this.message = 'Inscription réussie !';
          this.$router.push('/login');
        },
        error => {
          this.successful = false;
          this.message = (error.response && error.response.data && error.response.data.message) ||
            error.message ||
            error.toString();
        }
      ).finally(() => {
        this.loading = false;
      });
    }
  }
};
</script>

<style scoped>
.register {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style> 