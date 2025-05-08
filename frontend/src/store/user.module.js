import UserService from '../services/user.service';

const user = {
  namespaced: true,
  state: () => ({
    users: [],
    currentUser: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_USERS(state, users) {
      state.users = users;
    },
    SET_CURRENT_USER(state, user) {
      state.currentUser = user;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchUsers({ commit }) {
      commit('SET_LOADING', true);
      try {
        const response = await UserService.getAll();
        commit('SET_USERS', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération des utilisateurs');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchUser({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await UserService.get(id);
        commit('SET_CURRENT_USER', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de l\'utilisateur');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async updateUser({ commit }, { id, userData }) {
      commit('SET_LOADING', true);
      try {
        const response = await UserService.update(id, userData);
        commit('SET_CURRENT_USER', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la mise à jour de l\'utilisateur');
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allUsers: state => state.users,
    currentUser: state => state.currentUser,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default user; 