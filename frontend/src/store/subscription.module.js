import SubscriptionService from '../services/subscription.service';

const state = {
  plans: [],
  currentSubscription: null,
  paymentHistory: [],
  loading: false,
  error: null
};

const mutations = {
  SET_PLANS(state, plans) {
    state.plans = plans || [];
  },
  SET_CURRENT_SUBSCRIPTION(state, subscription) {
    state.currentSubscription = subscription;
  },
  SET_PAYMENT_HISTORY(state, history) {
    state.paymentHistory = history || [];
  },
  SET_LOADING(state, loading) {
    state.loading = loading;
  },
  SET_ERROR(state, error) {
    state.error = error;
  }
};

const actions = {
  async fetchPlans({ commit }) {
    console.log("Action fetchPlans démarrée");
    commit('SET_LOADING', true);
    commit('SET_ERROR', null);
    try {
      const response = await SubscriptionService.getAvailablePlans();
      console.log("Plans reçus:", response.data);
      commit('SET_PLANS', response.data);
      return response.data;
    } catch (error) {
      console.error("Erreur fetchPlans:", error);
      const errorMessage = error.response?.data?.message || 'Erreur lors du chargement des plans';
      commit('SET_ERROR', errorMessage);
      commit('SET_PLANS', []);
      throw error;
    } finally {
      commit('SET_LOADING', false);
    }
  },

  async fetchCurrentSubscription({ commit }) {
    commit('SET_LOADING', true);
    commit('SET_ERROR', null);
    try {
      const response = await SubscriptionService.getCurrentSubscription();
      commit('SET_CURRENT_SUBSCRIPTION', response.data);
      return response.data;
    } catch (error) {
      const errorMessage = error.response?.data?.message || 'Erreur lors du chargement de l\'abonnement';
      commit('SET_ERROR', errorMessage);
      commit('SET_CURRENT_SUBSCRIPTION', null);
      if (error.response?.status !== 404) {
        throw error;
      }
    } finally {
      commit('SET_LOADING', false);
    }
  },

  async subscribe({ commit, dispatch }, planId) {
    commit('SET_LOADING', true);
    commit('SET_ERROR', null);
    try {
      const response = await SubscriptionService.subscribe(planId);
      await dispatch('fetchCurrentSubscription');
      return response.data;
    } catch (error) {
      const errorMessage = error.response?.data?.message || 'Erreur lors de la souscription';
      commit('SET_ERROR', errorMessage);
      throw error;
    } finally {
      commit('SET_LOADING', false);
    }
  },

  async cancelSubscription({ commit, dispatch }) {
    commit('SET_LOADING', true);
    commit('SET_ERROR', null);
    try {
      const response = await SubscriptionService.cancelSubscription();
      await dispatch('fetchCurrentSubscription');
      return response.data;
    } catch (error) {
      const errorMessage = error.response?.data?.message || 'Erreur lors de l\'annulation';
      commit('SET_ERROR', errorMessage);
      throw error;
    } finally {
      commit('SET_LOADING', false);
    }
  },

  async fetchPaymentHistory({ commit }) {
    commit('SET_LOADING', true);
    commit('SET_ERROR', null);
    try {
      const response = await SubscriptionService.getSubscriptionHistory();
      commit('SET_PAYMENT_HISTORY', response.data);
      return response.data;
    } catch (error) {
      const errorMessage = error.response?.data?.message || 'Erreur lors du chargement de l\'historique';
      commit('SET_ERROR', errorMessage);
      commit('SET_PAYMENT_HISTORY', []);
      throw error;
    } finally {
      commit('SET_LOADING', false);
    }
  }
};

const getters = {
  hasActiveSubscription: state => {
    return state.currentSubscription && state.currentSubscription.status === 'active';
  },
  currentPlan: state => {
    return state.currentSubscription ? state.currentSubscription.plan : null;
  },
  isLoading: state => state.loading,
  getError: state => state.error,
  getPlans: state => state.plans
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}; 