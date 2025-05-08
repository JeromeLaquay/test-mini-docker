import FactureService from '../services/facture.service';

const facture = {
  namespaced: true,
  state: () => ({
    factures: [],
    currentFacture: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_FACTURES(state, factures) {
      state.factures = factures;
    },
    SET_CURRENT_FACTURE(state, facture) {
      state.currentFacture = facture;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchFactures({ commit }) {
      commit('SET_LOADING', true);
      try {
        const response = await FactureService.getAll();
        commit('SET_FACTURES', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération des factures');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchFacture({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await FactureService.get(id);
        commit('SET_CURRENT_FACTURE', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de la facture');
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allFactures: state => state.factures,
    currentFacture: state => state.currentFacture,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default facture; 