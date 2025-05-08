import SubscriptionService from '../services/subscription.service';

const subscription = {
  namespaced: true,
  state: () => ({
    abonnements: [],
    currentAbonnement: null,
    loading: false,
    error: null
  }),
  mutations: {
    setPlans(state, plans) {
      state.abonnements = plans;
    },
    setCurrentSubscription(state, subscription) {
      state.currentAbonnement = subscription;
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
  },
  actions: {
    // Récupérer tous les plans d'abonnement
    async fetchPlans({ commit }) {
      commit('setLoading', true);
      commit('clearError');
      try {
        const response = await SubscriptionService.getPlans();
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
        const response = await SubscriptionService.getCurrentSubscription();
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
        await SubscriptionService.subscribe(planId);
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
        await SubscriptionService.cancelSubscription();
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
        const response = await SubscriptionService.getPaymentHistory();
        commit('setPaymentHistory', response.data);
      } catch (error) {
        commit('setError', error.response?.data?.message || 'Erreur lors de la récupération de l\'historique des paiements');
        throw error;
      } finally {
        commit('setLoading', false);
      }
    }
  },
  getters: {
    plans: state => state.abonnements,
    currentSubscription: state => state.currentAbonnement,
    paymentHistory: state => state.paymentHistory,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default subscription; 