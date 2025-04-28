<template>
  <nav class="bg-gray-900/80 backdrop-blur-sm shadow-lg border-b border-gray-700">
    <div class="container mx-auto px-4 py-3 flex justify-between items-center">
      <router-link to="/home" class="flex items-center space-x-3">
        <img src="@/assets/x-logo.png" alt="JeroKa Logo" class="w-12 h-12 object-contain" />
        <span class="text-white font-bold text-2xl">JeroKa</span>
      </router-link>
      <!-- Menu desktop -->
      <div class="hidden md:flex items-center space-x-6">
        <router-link to="/home" class="text-gray-300 hover:text-white flex items-center space-x-2" active-class="text-blue-400">
          <span>Accueil</span>
        </router-link>
        <router-link to="/albums" class="text-gray-300 hover:text-white flex items-center space-x-2" active-class="text-blue-400">
          <span>Albums</span>
        </router-link>
        <router-link to="/subscriptions" class="text-gray-300 hover:text-white flex items-center space-x-2" active-class="text-blue-400">
          <span>Abonnements</span>
        </router-link>
        <router-link v-if="currentUser" to="/profile" class="text-gray-300 hover:text-white flex items-center space-x-2" active-class="text-blue-400">
          <span>Profil</span>
        </router-link>
        <router-link v-if="isAdmin" to="/admin" class="text-gray-300 hover:text-white flex items-center space-x-2" active-class="text-blue-400">
          <span>Administration</span>
        </router-link>
        <button v-if="currentUser" @click="logOut" class="text-gray-300 hover:text-white flex items-center space-x-2">
          <span>Déconnexion</span>
        </button>
        <router-link v-if="!currentUser" to="/login" class="text-gray-300 hover:text-white flex items-center space-x-2">
          <span>Connexion</span>
        </router-link>
        <router-link v-if="!currentUser" to="/register" class="text-gray-300 hover:text-white flex items-center space-x-2">
          <span>Inscription</span>
        </router-link>
      </div>
      <!-- Burger menu button -->
      <button @click="toggleMenu" class="md:hidden flex flex-col justify-center items-center w-10 h-10 focus:outline-none group">
        <span :class="[ 'block h-0.5 w-6 bg-white transition-all duration-300', menuOpen ? 'rotate-45 translate-y-2' : '' ]"></span>
        <span :class="[ 'block h-0.5 w-6 bg-white my-1 transition-all duration-300', menuOpen ? 'opacity-0' : '' ]"></span>
        <span :class="[ 'block h-0.5 w-6 bg-white transition-all duration-300', menuOpen ? '-rotate-45 -translate-y-2' : '' ]"></span>
      </button>
    </div>
    <!-- Mobile menu -->
    <transition name="fade">
      <div
        v-if="menuOpen"
        class="md:hidden fixed inset-0 z-[9999] bg-black/90 flex flex-col items-start justify-start px-0 py-0 overflow-y-auto"
      >
        <div class="w-full flex items-center justify-between px-4 pt-4 pb-2 bg-gray-900/90">
          <router-link to="/home" @click="closeMenu" class="flex items-center space-x-3">
            <img src="@/assets/x-logo.png" alt="JeroKa Logo" class="w-10 h-10 object-contain" />
            <span class="text-white font-bold text-xl">JeroKa</span>
          </router-link>
          <button @click="closeMenu" class="text-white text-3xl px-2 focus:outline-none">&times;</button>
        </div>
        <div class="flex flex-col w-full space-y-4 px-6 py-6 menu-debug min-h-[200px]">
          <a href="#" style="color: yellow !important; background: blue !important; opacity: 1 !important; font-size: 2rem !important; display: block !important;">TEST VISIBLE INLINE</a>
          <div style="color:lime;background:black;">TEST ABSOLU</div>
          <router-link @click="closeMenu" to="/home">Accueil</router-link>
          <router-link @click="closeMenu" to="/albums">Albums</router-link>
          <router-link @click="closeMenu" to="/subscriptions">Abonnements</router-link>
          <router-link v-if="currentUser" @click="closeMenu" to="/profile">Profil</router-link>
          <router-link v-if="isAdmin" @click="closeMenu" to="/admin">Administration</router-link>
          <button v-if="currentUser" @click="logOutAndClose">Déconnexion</button>
          <router-link v-if="!currentUser" @click="closeMenu" to="/login">Connexion</router-link>
          <router-link v-if="!currentUser" @click="closeMenu" to="/register">Inscription</router-link>
        </div>
      </div>
    </transition>
  </nav>
</template>

<script>
import { ref } from 'vue';
export default {
  setup() {
    const menuOpen = ref(false);
    const toggleMenu = () => { menuOpen.value = !menuOpen.value; };
    const closeMenu = () => { menuOpen.value = false; };
    return { menuOpen, toggleMenu, closeMenu };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    isAdmin() {
      return this.currentUser && this.currentUser.roles && this.currentUser.roles.includes('ROLE_ADMIN');
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    logOutAndClose() {
      this.logOut();
      this.$nextTick(() => this.menuOpen = false);
    }
  }
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.bg-red-500, .bg-green-500, .bg-blue-500, .bg-yellow-500 {
  opacity: 1 !important;
  display: block !important;
  color: #000 !important;
}

.menu-debug a, .menu-debug button {
  color: #fff !important;
  background: #1e40af !important; /* bleu vif */
  opacity: 1 !important;
  display: block !important;
  font-size: 1.5rem !important;
  z-index: 99999 !important;
}
.menu-debug {
  background: #f87171 !important; /* rouge vif */
  opacity: 1 !important;
  z-index: 99999 !important;
}
</style>
