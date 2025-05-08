import HistoriqueService from '../services/historique.service';

const historique = {
  namespaced: true,
  state: () => ({
    historiques: [],
    currentHistorique: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_HISTORIQUES(state, historiques) {
      state.historiques = historiques;
    },
    SET_CURRENT_HISTORIQUE(state, historique) {
      state.currentHistorique = historique;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchHistoriques({ commit }) {
      commit('SET_LOADING', true);
      try {
        const response = await HistoriqueService.getAll();
        commit('SET_HISTORIQUES', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de l\'historique');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchHistoriqueById({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await HistoriqueService.get(id);
        commit('SET_CURRENT_HISTORIQUE', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de l\'historique');
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allHistoriques: state => state.historiques,
    currentHistorique: state => state.currentHistorique,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default historique; 