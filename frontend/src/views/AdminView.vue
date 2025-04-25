<template>
  <div class="admin min-h-screen bg-gradient-to-br from-gray-900 via-blue-900 to-indigo-900">
    <header class="bg-gray-900/80 backdrop-blur-sm shadow-lg border-b border-gray-700">
      <div class="mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <h1 class="text-3xl font-bold text-white flex items-center">
          <span class="mr-3">🎛️</span>
          Administration
        </h1>
      </div>
    </header>
    <main>
      <div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <!-- Menu de navigation d'administration -->
        <div class="bg-gray-800/90 shadow-lg mb-6 rounded-xl overflow-hidden border border-gray-700">
          <nav class="flex">
            <router-link to="/admin" class="px-4 py-3 text-gray-300 hover:bg-gray-700 font-medium transition-colors" exact-active-class="bg-blue-600 text-white">
              <span class="flex items-center">
                <span class="mr-2">📊</span>
                Tableau de bord
              </span>
            </router-link>
            <router-link to="/users" class="px-4 py-3 text-gray-300 hover:bg-gray-700 font-medium transition-colors" exact-active-class="bg-blue-600 text-white">
              <span class="flex items-center">
                <span class="mr-2">👥</span>
                Gestion des utilisateurs
              </span>
            </router-link>
          </nav>
        </div>
        
        <div class="px-4 py-6 sm:px-0">
          <div class="bg-gray-800/90 p-8 rounded-xl shadow-2xl border border-gray-700">
            <h2 class="text-xl font-semibold mb-4 text-white">{{ content }}</h2>
            
            <!-- Cards des statistiques -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
              <div class="bg-gray-900 overflow-hidden rounded-xl border border-gray-700">
                <div class="p-5">
                  <div class="flex items-center">
                    <div class="flex-shrink-0 bg-blue-600 rounded-lg p-3">
                      <span class="text-2xl">👥</span>
                    </div>
                    <div class="ml-5 w-0 flex-1">
                      <dl>
                        <dt class="text-sm font-medium text-gray-400 truncate">Utilisateurs</dt>
                        <dd>
                          <div class="text-lg font-medium text-white">20</div>
                        </dd>
                      </dl>
                    </div>
                  </div>
                </div>
                <div class="bg-gray-800 px-5 py-3">
                  <div class="text-sm">
                    <router-link to="/users" class="font-medium text-blue-400 hover:text-blue-300 transition-colors">Voir tous les utilisateurs</router-link>
                  </div>
                </div>
              </div>
              
              <div class="bg-gray-900 overflow-hidden rounded-xl border border-gray-700">
                <div class="p-5">
                  <div class="flex items-center">
                    <div class="flex-shrink-0 bg-green-600 rounded-lg p-3">
                      <span class="text-2xl">✅</span>
                    </div>
                    <div class="ml-5 w-0 flex-1">
                      <dl>
                        <dt class="text-sm font-medium text-gray-400 truncate">Utilisateurs actifs</dt>
                        <dd>
                          <div class="text-lg font-medium text-white">15</div>
                        </dd>
                      </dl>
                    </div>
                  </div>
                </div>
                <div class="bg-gray-800 px-5 py-3">
                  <div class="text-sm">
                    <a href="#" class="font-medium text-green-400 hover:text-green-300 transition-colors">Consulter les statistiques</a>
                  </div>
                </div>
              </div>
              
              <div class="bg-gray-900 overflow-hidden rounded-xl border border-gray-700">
                <div class="p-5">
                  <div class="flex items-center">
                    <div class="flex-shrink-0 bg-red-600 rounded-lg p-3">
                      <span class="text-2xl">⚠️</span>
                    </div>
                    <div class="ml-5 w-0 flex-1">
                      <dl>
                        <dt class="text-sm font-medium text-gray-400 truncate">Alertes système</dt>
                        <dd>
                          <div class="text-lg font-medium text-white">2</div>
                        </dd>
                      </dl>
                    </div>
                  </div>
                </div>
                <div class="bg-gray-800 px-5 py-3">
                  <div class="text-sm">
                    <a href="#" class="font-medium text-red-400 hover:text-red-300 transition-colors">Voir les alertes</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>


export default {
  name: 'AdminView',
  data() {
    return {
      content: 'Bienvenue dans le panneau d\'administration'
    };
  },
  mounted() {
//    UserService.getAdminBoard().then(
//      response => {
//        if (response.data) {
//          this.content = response.data;
//        }
//      },
//      error => {
//        this.content =
//          (error.response && error.response.data && error.response.data.message) ||
//          error.message ||
//          error.toString();
//      }
//    );
  }
};
</script>

<style scoped>
.admin {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.05) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style> 