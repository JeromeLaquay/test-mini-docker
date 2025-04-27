import subscriptionService from '../services/subscription.service';

const state = {
  plans: [],
  currentSubscription: null,
  paymentHistory: [],
  loading: false,
  error: null
};

const getters = {
  plans: state => state.plans,
  currentSubscription: state => state.currentSubscription,
  paymentHistory: state => state.paymentHistory,
  loading: state => state.loading,
  error: state => state.error
};

const actions = {
  // Récupérer tous les plans d'abonnement
  async fetchPlans({ commit }) {
    commit('setLoading', true);
    commit('clearError');
    try {
      const response = await subscriptionService.getPlans();
      commit('setPlans', response.data);
    } catch (error) {
      commit('setError', error.response?.data?.message || 'Erreur lors de la récupération des plans');
      throw error;
    } finally {
      commit('setLoading', false);
    }
  },

  // Récupérer l'abonnement actuel
  async fetchCurrentSubscription({ commit }) {
    commit('setLoading', true);
    commit('clearError');
    try {
      const response = await subscriptionService.getCurrentSubscription();
      commit('setCurrentSubscription', response.data);
    } catch (error) {
      if (error.response?.status !== 404) {
        commit('setError', error.response?.data?.message || 'Erreur lors de la récupération de l\'abonnement actuel');
      }
      commit('setCurrentSubscription', null);
      throw error;
    } finally {
      commit('setLoading', false);
    }
  },

  // S'abonner à un plan
  async subscribe({ commit, dispatch }, planId) {
    commit('setLoading', true);
    commit('clearError');
    try {
      await subscriptionService.subscribe(planId);
      // Rafraîchir l'abonnement actuel après la souscription
      await dispatch('fetchCurrentSubscription');
    } catch (error) {
      commit('setError', error.response?.data?.message || 'Erreur lors de la souscription');
      throw error;
    } finally {
      commit('setLoading', false);
    }
  },

  // Annuler l'abonnement actuel
  async cancelSubscription({ commit }) {
    commit('setLoading', true);
    commit('clearError');
    try {
      await subscriptionService.cancelSubscription();
      commit('setCurrentSubscription', null);
    } catch (error) {
      commit('setError', error.response?.data?.message || 'Erreur lors de l\'annulation de l\'abonnement');
      throw error;
    } finally {
      commit('setLoading', false);
    }
  },

  // Récupérer l'historique des paiements
  async fetchPaymentHistory({ commit }) {
    commit('setLoading', true);
    commit('clearError');
    try {
      const response = await subscriptionService.getPaymentHistory();
      commit('setPaymentHistory', response.data);
    } catch (error) {
      commit('setError', error.response?.data?.message || 'Erreur lors de la récupération de l\'historique des paiements');
      throw error;
    } finally {
      commit('setLoading', false);
    }
  }
};

const mutations = {
  setPlans(state, plans) {
    state.plans = plans;
  },
  setCurrentSubscription(state, subscription) {
    state.currentSubscription = subscription;
  },
  setPaymentHistory(state, history) {
    state.paymentHistory = history;
  },
  setLoading(state, loading) {
    state.loading = loading;
  },
  setError(state, error) {
    state.error = error;
  },
  clearError(state) {
    state.error = null;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}; 