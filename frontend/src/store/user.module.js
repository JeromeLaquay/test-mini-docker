import UserService from '../services/user.service';

const state = {
  users: [],
  currentUser: null,
  loading: false,
  error: null
};

const getters = {
  allUsers: state => state.users,
  currentUser: state => state.currentUser,
  isLoading: state => state.loading,
  error: state => state.error
};

const actions = {
  fetchAllUsers({ commit }) {
    commit('setLoading', true);
    return UserService.getAllUsers()
      .then(response => {
        commit('setUsers', response.data);
        commit('setLoading', false);
        return Promise.resolve(response.data);
      })
      .catch(error => {
        commit('setError', error.response?.data || 'Erreur lors de la récupération des utilisateurs');
        commit('setLoading', false);
        return Promise.reject(error);
      });
  },
  fetchUserById({ commit }, id) {
    commit('setLoading', true);
    return UserService.getUserById(id)
      .then(response => {
        commit('setCurrentUser', response.data);
        commit('setLoading', false);
        return Promise.resolve(response.data);
      })
      .catch(error => {
        commit('setError', error.response?.data || 'Erreur lors de la récupération de l\'utilisateur');
        commit('setLoading', false);
        return Promise.reject(error);
      });
  },
  createUser({ commit }, userData) {
    commit('setLoading', true);
    return UserService.createUser(userData)
      .then(response => {
        commit('addUser', response.data);
        commit('setLoading', false);
        return Promise.resolve(response.data);
      })
      .catch(error => {
        commit('setError', error.response?.data || 'Erreur lors de la création de l\'utilisateur');
        commit('setLoading', false);
        return Promise.reject(error);
      });
  },
  updateUser({ commit }, { id, userData }) {
    commit('setLoading', true);
    return UserService.updateUser(id, userData)
      .then(response => {
        commit('updateUserInList', response.data);
        commit('setLoading', false);
        return Promise.resolve(response.data);
      })
      .catch(error => {
        commit('setError', error.response?.data || 'Erreur lors de la mise à jour de l\'utilisateur');
        commit('setLoading', false);
        return Promise.reject(error);
      });
  },
  deleteUser({ commit }, id) {
    commit('setLoading', true);
    return UserService.deleteUser(id)
      .then(() => {
        commit('removeUser', id);
        commit('setLoading', false);
        return Promise.resolve();
      })
      .catch(error => {
        commit('setError', error.response?.data || 'Erreur lors de la suppression de l\'utilisateur');
        commit('setLoading', false);
        return Promise.reject(error);
      });
  },
  clearError({ commit }) {
    commit('setError', null);
  }
};

const mutations = {
  setUsers(state, users) {
    state.users = users;
  },
  setCurrentUser(state, user) {
    state.currentUser = user;
  },
  addUser(state, user) {
    state.users.push(user);
  },
  updateUserInList(state, updatedUser) {
    const index = state.users.findIndex(user => user.id === updatedUser.id);
    if (index !== -1) {
      state.users.splice(index, 1, updatedUser);
    }
    if (state.currentUser && state.currentUser.id === updatedUser.id) {
      state.currentUser = updatedUser;
    }
  },
  removeUser(state, id) {
    state.users = state.users.filter(user => user.id !== id);
    if (state.currentUser && state.currentUser.id === id) {
      state.currentUser = null;
    }
  },
  setLoading(state, status) {
    state.loading = status;
  },
  setError(state, error) {
    state.error = error;
  }
};

export const user = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}; 