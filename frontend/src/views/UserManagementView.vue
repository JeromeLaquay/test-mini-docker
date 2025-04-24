<template>
  <div class="user-management">
    <h1 class="text-2xl font-bold mb-6">Gestion des Utilisateurs</h1>
    
    <!-- Notification d'erreur -->
    <div v-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
      <span>{{ error }}</span>
      <button @click="clearError" class="float-right">&times;</button>
    </div>
    
    <!-- Bouton pour ajouter un nouvel utilisateur -->
    <div class="mb-6 flex justify-between">
      <button @click="showAddForm = !showAddForm" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        {{ showAddForm ? 'Annuler' : 'Ajouter un utilisateur' }}
      </button>
      <div>
        <button @click="refreshToken" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2">
          Actualiser le token
        </button>
        <button @click="fetchAllUsers" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
          Rafraîchir
        </button>
      </div>
    </div>
    
    <!-- Formulaire d'ajout d'utilisateur -->
    <div v-if="showAddForm" class="bg-white rounded-lg shadow-md p-6 mb-6">
      <h2 class="text-xl font-semibold mb-4">Ajouter un Utilisateur</h2>
      <form @submit.prevent="handleCreateUser">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
              Nom d'utilisateur
            </label>
            <input v-model="newUser.username" id="username" type="text" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
          </div>
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="email">
              Email
            </label>
            <input v-model="newUser.email" id="email" type="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
          </div>
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
              Mot de passe
            </label>
            <input v-model="newUser.password" id="password" type="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
          </div>
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="roles">
              Rôles
            </label>
            <div class="mt-2">
              <label class="inline-flex items-center mr-4">
                <input type="checkbox" v-model="adminRole" class="form-checkbox h-5 w-5 text-blue-600">
                <span class="ml-2 text-gray-700">Admin</span>
              </label>
              <label class="inline-flex items-center mr-4">
                <input type="checkbox" v-model="modRole" class="form-checkbox h-5 w-5 text-blue-600">
                <span class="ml-2 text-gray-700">Modérateur</span>
              </label>
              <label class="inline-flex items-center">
                <input type="checkbox" v-model="userRole" class="form-checkbox h-5 w-5 text-blue-600">
                <span class="ml-2 text-gray-700">Utilisateur</span>
              </label>
            </div>
          </div>
        </div>
        <div class="flex items-center justify-end">
          <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
            Créer
          </button>
        </div>
      </form>
    </div>
    
    <!-- Indicateur de chargement -->
    <div v-if="loading" class="text-center py-4">
      <div class="inline-block animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-gray-900"></div>
      <p class="mt-2 text-gray-600">Chargement en cours...</p>
    </div>
    
    <!-- Liste des utilisateurs -->
    <div v-else class="bg-white rounded-lg shadow-md overflow-hidden">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nom d'utilisateur</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rôles</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="user in users" :key="user.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ user.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ user.username }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ user.email }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              <span v-for="role in user.roles" :key="role" class="px-2 py-1 mr-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                {{ role.replace('ROLE_', '') }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button @click="editUser(user)" class="text-indigo-600 hover:text-indigo-900 mr-3">Modifier</button>
              <button @click="confirmDelete(user)" class="text-red-600 hover:text-red-900">Supprimer</button>
            </td>
          </tr>
          <tr v-if="users.length === 0">
            <td colspan="5" class="px-6 py-4 text-center text-gray-500">Aucun utilisateur trouvé</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- Modal de confirmation de suppression -->
    <div v-if="showDeleteModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full p-6">
        <h3 class="text-lg font-bold mb-4">Confirmer la suppression</h3>
        <p class="mb-6">Êtes-vous sûr de vouloir supprimer l'utilisateur {{ userToDelete?.username }} ?</p>
        <div class="flex justify-end space-x-3">
          <button @click="showDeleteModal = false" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">
            Annuler
          </button>
          <button @click="deleteSelectedUser" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
            Supprimer
          </button>
        </div>
      </div>
    </div>
    
    <!-- Modal d'édition d'utilisateur -->
    <div v-if="showEditModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-2xl w-full p-6">
        <h3 class="text-lg font-bold mb-4">Modifier l'utilisateur</h3>
        <form @submit.prevent="updateSelectedUser">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="edit-username">
                Nom d'utilisateur
              </label>
              <input v-model="editingUser.username" id="edit-username" type="text" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="edit-email">
                Email
              </label>
              <input v-model="editingUser.email" id="edit-email" type="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="edit-password">
                Mot de passe (laisser vide pour ne pas modifier)
              </label>
              <input v-model="editingUser.password" id="edit-password" type="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="edit-roles">
                Rôles
              </label>
              <div class="mt-2">
                <label class="inline-flex items-center mr-4">
                  <input type="checkbox" v-model="adminRole" class="form-checkbox h-5 w-5 text-blue-600">
                  <span class="ml-2 text-gray-700">Admin</span>
                </label>
                <label class="inline-flex items-center mr-4">
                  <input type="checkbox" v-model="modRole" class="form-checkbox h-5 w-5 text-blue-600">
                  <span class="ml-2 text-gray-700">Modérateur</span>
                </label>
                <label class="inline-flex items-center">
                  <input type="checkbox" v-model="userRole" class="form-checkbox h-5 w-5 text-blue-600">
                  <span class="ml-2 text-gray-700">Utilisateur</span>
                </label>
              </div>
            </div>
          </div>
          <div class="flex justify-end space-x-3">
            <button type="button" @click="showEditModal = false" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">
              Annuler
            </button>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
              Enregistrer
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex';

export default {
  name: 'UserManagementView',
  data() {
    return {
      showAddForm: false,
      showDeleteModal: false,
      showEditModal: false,
      userToDelete: null,
      editingUser: {
        username: '',
        email: '',
        password: '',
        roles: []
      },
      newUser: {
        username: '',
        email: '',
        password: '',
        roles: []
      },
      adminRole: false,
      modRole: false,
      userRole: false
    };
  },
  computed: {
    ...mapState('user', ['loading', 'error']),
    ...mapGetters('user', { users: 'allUsers' })
  },
  methods: {
    ...mapActions('user', [
      'fetchAllUsers',
      'createUser',
      'updateUser',
      'deleteUser',
      'clearError'
    ]),
    ...mapActions('auth', [
      'login'
    ]),
    refreshToken() {
      // Récupérer les informations de l'utilisateur actuel
      const userStr = localStorage.getItem('user');
      if (!userStr) {
        this.error = "Aucun utilisateur connecté";
        return;
      }
      
      const user = JSON.parse(userStr);
      
      // Reconnecter l'utilisateur pour obtenir un nouveau token
      this.login({ username: user.username, password: prompt("Pour actualiser votre session, veuillez entrer votre mot de passe:") })
        .then(() => {
          this.error = "Token actualisé avec succès";
          setTimeout(() => {
            this.clearError();
            this.fetchAllUsers();
          }, 2000);
        })
        .catch(error => {
          this.error = "Échec de l'actualisation du token: " + (error.message || "Erreur inconnue");
        });
    },
    async handleCreateUser() {
      try {
        // Préparation des rôles en fonction des checkboxes
        const roles = [];
        if (this.adminRole) roles.push('ROLE_ADMIN');
        if (this.modRole) roles.push('ROLE_MODERATOR');
        if (this.userRole) roles.push('ROLE_USER');
        
        // Si aucun rôle n'est sélectionné, attribuer ROLE_USER par défaut
        if (roles.length === 0) {
          roles.push('ROLE_USER');
        }
        
        // Création de l'objet utilisateur à envoyer au backend
        const userData = {
          username: this.newUser.username,
          email: this.newUser.email,
          password: this.newUser.password,
          roles: roles
        };
        
        await this.createUser(userData);
        this.showAddForm = false;
        this.resetNewUser();
      } catch (error) {
        console.error('Erreur lors de la création de l\'utilisateur:', error);
        if (error.response && error.response.status === 401) {
          this.error = "Autorisation refusée. Votre session a peut-être expiré. Essayez d'actualiser votre token.";
        } else {
          this.error = "Erreur lors de la création de l'utilisateur: " + (error.message || "Erreur inconnue");
        }
      }
    },
    resetNewUser() {
      this.newUser = {
        username: '',
        email: '',
        password: '',
        roles: []
      };
      this.adminRole = false;
      this.modRole = false;
      this.userRole = false;
    },
    editUser(user) {
      this.editingUser = { 
        id: user.id,
        username: user.username,
        email: user.email,
        roles: user.roles || []
      };
      
      // Initialiser les checkboxes de rôles
      this.adminRole = user.roles?.includes('ROLE_ADMIN') || false;
      this.modRole = user.roles?.includes('ROLE_MODERATOR') || false;
      this.userRole = user.roles?.includes('ROLE_USER') || false;
      
      this.showEditModal = true;
    },
    async updateSelectedUser() {
      try {
        // Préparation des rôles en fonction des checkboxes
        const roles = [];
        if (this.adminRole) roles.push('ROLE_ADMIN');
        if (this.modRole) roles.push('ROLE_MODERATOR');
        if (this.userRole) roles.push('ROLE_USER');
        
        // Si aucun rôle n'est sélectionné, attribuer ROLE_USER par défaut
        if (roles.length === 0) {
          roles.push('ROLE_USER');
        }
        
        // Création de l'objet utilisateur à envoyer au backend
        const userData = {
          username: this.editingUser.username,
          email: this.editingUser.email,
          roles: roles
        };
        
        // Ajouter un mot de passe uniquement s'il a été saisi
        if (this.editingUser.password) {
          userData.password = this.editingUser.password;
        }
        
        await this.updateUser({
          id: this.editingUser.id,
          userData: userData
        });
        this.showEditModal = false;
      } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'utilisateur:', error);
        if (error.response && error.response.status === 401) {
          this.error = "Autorisation refusée. Votre session a peut-être expiré. Essayez d'actualiser votre token.";
        } else {
          this.error = "Erreur lors de la mise à jour de l'utilisateur: " + (error.message || "Erreur inconnue");
        }
      }
    },
    confirmDelete(user) {
      this.userToDelete = user;
      this.showDeleteModal = true;
    },
    async deleteSelectedUser() {
      if (this.userToDelete) {
        try {
          await this.deleteUser(this.userToDelete.id);
          this.showDeleteModal = false;
          this.userToDelete = null;
        } catch (error) {
          console.error('Erreur lors de la suppression de l\'utilisateur:', error);
          if (error.response && error.response.status === 401) {
            this.error = "Autorisation refusée. Votre session a peut-être expiré. Essayez d'actualiser votre token.";
          } else {
            this.error = "Erreur lors de la suppression de l'utilisateur: " + (error.message || "Erreur inconnue");
          }
        }
      }
    }
  },
  mounted() {
    this.fetchAllUsers().catch(error => {
      console.error('Erreur lors du chargement des utilisateurs:', error);
      if (error.response && error.response.status === 401) {
        this.error = "Autorisation refusée. Votre session a peut-être expiré. Essayez d'actualiser votre token.";
      } else {
        this.error = "Erreur lors du chargement des utilisateurs: " + (error.message || "Erreur inconnue");
      }
    });
  }
};
</script> 