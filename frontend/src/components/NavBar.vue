        <!-- Ajouter ce lien dans la section de navigation -->
        <router-link
          to="/subscription"
          class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
          :class="{ 'bg-gray-900 text-white': $route.path === '/subscription' }"
        >
          Abonnements
        </router-link> 