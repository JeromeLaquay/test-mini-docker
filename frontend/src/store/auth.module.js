import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

const auth = {
  namespaced: true,
  state: initialState,
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
      console.log('State after login:', state); // Debug
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    }
  },
  actions: {
    async login({ commit }, { username, password }) {
      try {
        const response = await AuthService.login(username, password);
        console.log('Login response:', response); // Debug

        const userData = {
          ...response,
          token: response.token || response.accessToken
        };

        console.log('User data to commit:', userData); // Debug
        commit('loginSuccess', userData);
        return userData;
      } catch (error) {
        console.error('Login error in module:', error);
        commit('loginFailure');
        throw error;
      }
    },
    logout({ commit }) {
      AuthService.logout();
      commit('logout');
    },
    async signup({ commit }, userData) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await AuthService.signup(userData);
        return response.data;
      } catch (error) {
        const message = error.response?.data?.message || 'Erreur lors de l\'inscription';
        commit('SET_ERROR', message);
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async refreshToken({ commit }) {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user && user.refreshToken) {
          const response = await AuthService.refreshToken(user.refreshToken);
          const newUser = { ...user, accessToken: response.data.accessToken };
          localStorage.setItem('user', JSON.stringify(newUser));
          commit('SET_USER', newUser);
        }
      } catch (error) {
        commit('LOGOUT');
        throw error;
      }
    }
  },
  getters: {
    isLoggedIn: state => state.status.loggedIn,
    currentUser: state => state.user,
    userToken: state => state.user?.token,
    loading: state => state.loading,
    error: state => state.error,
    isAdmin: state => state.user?.roles?.includes('ROLE_ADMIN') || false
  }
};

export default auth; 