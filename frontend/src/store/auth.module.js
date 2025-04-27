import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const state = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

const getters = {
  isLoggedIn: state => state.status.loggedIn,
  user: state => state.user,
  isAdmin: state => state.user && state.user.roles && state.user.roles.includes('ROLE_ADMIN')
};

const actions = {
    signin({ commit }, user) {
    return AuthService.signin(user).then(
      user => {
        console.log('Login réussi, données utilisateur reçues:', Object.keys(user));
        if (user.token) {
          console.log('Token présent dans la réponse');
        } else {
          console.warn('⚠️ Token absent de la réponse!', user);
        }
        commit('loginSuccess', user);
        return Promise.resolve(user);
      },
      error => {
        console.error('Erreur de login:', error);
        commit('loginFailure');
        return Promise.reject(error);
      }
    );
  },
  logout({ commit }) {
    AuthService.logout();
    commit('logout');
  },
  signup({ commit }, user) {
    return AuthService.signup(user).then(
      response => {
        commit('registerSuccess');
        return Promise.resolve(response.data);
      },
      error => {
        commit('registerFailure');
        return Promise.reject(error);
      }
    );
  }
};

const mutations = {
  loginSuccess(state, user) {
    state.status.loggedIn = true;
    state.user = user;
  },
  loginFailure(state) {
    state.status.loggedIn = false;
    state.user = null;
  },
  logout(state) {
    state.status.loggedIn = false;
    state.user = null;
  },
  registerSuccess(state) {
    state.status.loggedIn = false;
  },
  registerFailure(state) {
    state.status.loggedIn = false;
  }
};

export const auth = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}; 