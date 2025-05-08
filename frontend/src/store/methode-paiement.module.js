import MethodePaiementService from '../services/methode-paiement.service';

const initialState = {
  methodesPaiement: [],
  currentMethodePaiement: null,
  loading: false,
  error: null
};

export default {
  namespaced: true,
  state: initialState,
  
  getters: {
    methodesPaiement: state => state.methodesPaiement,
    currentMethodePaiement: state => state.currentMethodePaiement,
    isLoading: state => state.loading,
    hasError: state => state.error !== null,
    error: state => state.error
  },

  mutations: {
    SET_METHODES_PAIEMENT(state, methodesPaiement) {
      state.methodesPaiement = methodesPaiement;
    },
    SET_CURRENT_METHODE_PAIEMENT(state, methodePaiement) {
      state.currentMethodePaiement = methodePaiement;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    },
    CLEAR_ERROR(state) {
      state.error = null;
    }
  },

  actions: {
    // Charger les méthodes de paiement d'un utilisateur
    async fetchMethodesPaiement({ commit }, userId) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await MethodePaiementService.getMethodesPaiementByUser(userId);
        commit('SET_METHODES_PAIEMENT', response.data);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors du chargement des méthodes de paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },

    // Charger une méthode de paiement spécifique
    async fetchMethodePaiement({ commit }, id) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await MethodePaiementService.getMethodePaiementById(id);
        commit('SET_CURRENT_METHODE_PAIEMENT', response.data);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors du chargement de la méthode de paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },

    // Créer une nouvelle méthode de paiement
    async createMethodePaiement({ commit }, methodePaiement) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await MethodePaiementService.createMethodePaiement(methodePaiement);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la création de la méthode de paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },

    // Mettre à jour une méthode de paiement
    async updateMethodePaiement({ commit }, { id, methodePaiement }) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await MethodePaiementService.updateMethodePaiement(id, methodePaiement);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la mise à jour de la méthode de paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },

    // Supprimer une méthode de paiement
    async deleteMethodePaiement({ commit }, id) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        await MethodePaiementService.deleteMethodePaiement(id);
        return true;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la suppression de la méthode de paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },

    async initializePayment({ commit }, paymentData) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      try {
        const response = await MethodePaiementService.initializePayment(paymentData);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de l\'initialisation du paiement');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    }
  }
  
}; 