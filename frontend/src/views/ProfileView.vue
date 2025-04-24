<template>
  <div class="profile min-h-screen bg-gradient-to-br from-gray-900 via-blue-900 to-indigo-900">
    <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
      <!-- Loading state -->
      <div v-if="loading" class="flex justify-center items-center h-64">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-white"></div>
      </div>

      <!-- Error message -->
      <div v-else-if="error" class="bg-red-600/20 border-l-4 border-red-600 p-4 rounded-lg" role="alert">
        <p class="font-bold text-red-200">Erreur</p>
        <p class="text-red-100">{{ error }}</p>
      </div>

      <div v-else-if="currentUser" class="space-y-6">
        <!-- En-tête du profil -->
        <div class="bg-gray-800/90 rounded-xl shadow-2xl border border-gray-700 overflow-hidden">
          <div class="relative h-32 bg-gradient-to-r from-blue-600 to-purple-600">
            <div class="absolute -bottom-12 left-8">
              <div class="w-24 h-24 bg-gray-800 rounded-full border-4 border-gray-800 flex items-center justify-center">
                <span class="text-4xl">{{ userInitials }}</span>
              </div>
            </div>
          </div>
          <div class="pt-16 pb-6 px-8">
            <div class="flex justify-between items-start">
              <div>
                <h1 class="text-2xl font-bold text-white">{{ userInfo.username }}</h1>
                <p class="text-gray-400">{{ userInfo.email }}</p>
              </div>
              <button 
                @click="handleEditMode" 
                class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors duration-200 flex items-center"
                :disabled="loading"
              >
                <span class="mr-2">{{ editMode ? '💾' : '✏️' }}</span>
                {{ editMode ? 'Sauvegarder' : 'Modifier' }}
              </button>
            </div>
          </div>
        </div>

        <!-- Informations principales -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <!-- Colonne de gauche : Informations personnelles -->
          <div class="space-y-6">
            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">👤</span>
                Informations personnelles
              </h2>
              <div class="space-y-4">
                <div>
                  <label class="block text-sm font-medium text-gray-400">Nom d'utilisateur</label>
                  <input
                    v-if="editMode"
                    v-model="userInfo.username"
                    class="mt-1 block w-full bg-gray-700 border border-gray-600 rounded-md shadow-sm py-2 px-3 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  <p v-else class="mt-1 text-white">{{ userInfo.username }}</p>
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-400">Email</label>
                  <input
                    v-if="editMode"
                    v-model="userInfo.email"
                    type="email"
                    class="mt-1 block w-full bg-gray-700 border border-gray-600 rounded-md shadow-sm py-2 px-3 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  <p v-else class="mt-1 text-white">{{ userInfo.email }}</p>
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-400">Date d'inscription</label>
                  <p class="mt-1 text-white">{{ formattedDate }}</p>
                </div>
              </div>
            </div>

            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">🔒</span>
                Sécurité
              </h2>
              <div class="space-y-4">
                <button 
                  @click="showChangePasswordModal = true" 
                  class="w-full px-4 py-2 bg-gray-700 text-white rounded-lg hover:bg-gray-600 transition-colors duration-200"
                >
                  Changer le mot de passe
                </button>
              </div>
            </div>
          </div>

          <!-- Colonne du milieu : Abonnement et statistiques -->
          <div class="space-y-6">
            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">⭐</span>
                Abonnement
              </h2>
              <div class="space-y-4">
                <div class="bg-gradient-to-r from-purple-600 to-blue-600 p-4 rounded-lg">
                  <p class="text-white font-semibold">Plan {{ subscription.plan }}</p>
                  <p class="text-sm text-white/80" v-if="subscription.expiryDate">
                    Expire le: {{ new Date(subscription.expiryDate).toLocaleDateString('fr-FR') }}
                  </p>
                  <div class="mt-2">
                    <ul class="text-sm text-white/90 space-y-1">
                      <li v-for="feature in subscription.features" :key="feature" class="flex items-center">
                        <span class="mr-2">✓</span>
                        {{ feature }}
                      </li>
                    </ul>
                  </div>
                </div>
                <button 
                  @click="manageSubscription"
                  class="w-full px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors duration-200"
                >
                  Gérer l'abonnement
                </button>
              </div>
            </div>

            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">📊</span>
                Statistiques
              </h2>
              <div class="grid grid-cols-2 gap-4">
                <div class="bg-gray-700/50 p-4 rounded-lg text-center">
                  <p class="text-2xl font-bold text-white">{{ stats.uploads }}</p>
                  <p class="text-sm text-gray-400">Uploads</p>
                </div>
                <div class="bg-gray-700/50 p-4 rounded-lg text-center">
                  <p class="text-2xl font-bold text-white">{{ stats.playlists }}</p>
                  <p class="text-sm text-gray-400">Playlists</p>
                </div>
                <div class="bg-gray-700/50 p-4 rounded-lg text-center">
                  <p class="text-2xl font-bold text-white">{{ stats.followers }}</p>
                  <p class="text-sm text-gray-400">Followers</p>
                </div>
                <div class="bg-gray-700/50 p-4 rounded-lg text-center">
                  <p class="text-2xl font-bold text-white">{{ stats.following }}</p>
                  <p class="text-sm text-gray-400">Following</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Colonne de droite : Activité récente et badges -->
          <div class="space-y-6">
            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">🎵</span>
                Activité récente
              </h2>
              <div class="space-y-4">
                <div v-for="activity in recentActivities" :key="activity.id" class="flex items-start space-x-3 p-3 bg-gray-700/50 rounded-lg">
                  <span class="text-xl">{{ activity.icon }}</span>
                  <div>
                    <p class="text-white">{{ activity.description }}</p>
                    <p class="text-sm text-gray-400">{{ activity.date }}</p>
                  </div>
                </div>
                <p v-if="recentActivities.length === 0" class="text-gray-400 text-center py-4">
                  Aucune activité récente
                </p>
              </div>
            </div>

            <div class="bg-gray-800/90 p-6 rounded-xl shadow-lg border border-gray-700">
              <h2 class="text-xl font-semibold text-white mb-4 flex items-center">
                <span class="mr-2">🏆</span>
                Badges
              </h2>
              <div class="grid grid-cols-3 gap-4">
                <div v-for="badge in badges" :key="badge.id" 
                  class="flex flex-col items-center p-3 bg-gray-700/50 rounded-lg hover:bg-gray-600/50 transition-colors duration-200 cursor-help"
                  :title="badge.description"
                >
                  <span class="text-2xl mb-1">{{ badge.icon }}</span>
                  <p class="text-sm text-center text-white">{{ badge.name }}</p>
                  <p class="text-xs text-center text-gray-400 mt-1">{{ badge.progress || '' }}</p>
                </div>
                <p v-if="badges.length === 0" class="text-gray-400 text-center py-4 col-span-3">
                  Aucun badge obtenu
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Message si non connecté -->
      <div v-else class="bg-yellow-600/20 border-l-4 border-yellow-600 p-4 rounded-lg" role="alert">
        <p class="font-bold text-yellow-200">Attention</p>
        <p class="text-yellow-100">
          Vous devez être connecté pour voir cette page. 
          <router-link to="/login" class="underline font-semibold text-blue-400 hover:text-blue-300">
            Se connecter
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import UserService from '@/services/user.service';
//import SubscriptionService from '@/services/subscription.service';

export default {
  name: 'ProfileView',
  setup() {
    const store = useStore();
    const router = useRouter();
    const editMode = ref(false);
    const showChangePasswordModal = ref(false);
    const loading = ref(false);
    const error = ref(null);

    const userInfo = ref({
      username: '',
      email: '',
      createdAt: null
    });

    const subscription = ref({
      plan: 'Gratuit',
      expiryDate: null,
      features: []
    });

    const stats = ref({
      uploads: 0,
      playlists: 0,
      followers: 0,
      following: 0
    });

    const recentActivities = ref([]);
    const badges = ref([]);

    // Computed properties
    const currentUser = computed(() => store.state.auth.user);
    const userInitials = computed(() => {
      if (!currentUser.value) return '';
      return currentUser.value.username
        .split(' ')
        .map(word => word[0])
        .join('')
        .toUpperCase();
    });

    const formattedDate = computed(() => {
      if (!userInfo.value.createdAt) return '';
      return new Date(userInfo.value.createdAt).toLocaleDateString('fr-FR', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
      });
    });

    // Methods
    const loadUserProfile = async () => {
      try {
        loading.value = true;
        const response = await UserService.getUserProfile();
        userInfo.value = {
          username: response.username,
          email: response.email,
          createdAt: response.createdAt
        };
        stats.value = response.stats || {
          uploads: 0,
          playlists: 0,
          followers: 0,
          following: 0
        };
        recentActivities.value = response.activities || [];
        badges.value = response.badges || [];
      } catch (err) {
        error.value = "Erreur lors du chargement du profil";
        console.error(err);
      } finally {
        loading.value = false;
      }
    };

//    const loadSubscriptionInfo = async () => {
//      try {
//        const subInfo = await SubscriptionService.getCurrentSubscription();
//        subscription.value = {
//            plan: subInfo.planName,
//          expiryDate: subInfo.expiryDate,
//          features: subInfo.features
//        };
//      } catch (err) {
//        console.error("Erreur lors du chargement de l'abonnement:", err);
//      }
//    };

    const saveProfile = async () => {
      try {
        loading.value = true;
        await UserService.updateProfile({
          username: userInfo.value.username,
          email: userInfo.value.email
        });
        editMode.value = false;
        await store.dispatch('auth/updateUserInfo', {
          username: userInfo.value.username,
          email: userInfo.value.email
        });
      } catch (err) {
        error.value = "Erreur lors de la sauvegarde du profil";
        console.error(err);
      } finally {
        loading.value = false;
      }
    };

    const handleEditMode = () => {
      if (editMode.value) {
        saveProfile();
      } else {
        editMode.value = true;
      }
    };

    const manageSubscription = () => {
      router.push('/subscription-plans');
    };

    const changePassword = async (oldPassword, newPassword) => {
      try {
        await UserService.changePassword(oldPassword, newPassword);
        showChangePasswordModal.value = false;
      } catch (err) {
        error.value = "Erreur lors du changement de mot de passe";
        console.error(err);
      }
    };

    onMounted(async () => {
      if (!currentUser.value) {
        router.push('/login');
        return;
      }
      await Promise.all([
        loadUserProfile(),
        //loadSubscriptionInfo()
      ]);
    });

    return {
      currentUser,
      userInitials,
      formattedDate,
      editMode,
      showChangePasswordModal,
      userInfo,
      subscription,
      stats,
      recentActivities,
      badges,
      loading,
      error,
      handleEditMode,
      manageSubscription,
      changePassword
    };
  }
};
</script>

<style scoped>
.profile {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.05) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style> 