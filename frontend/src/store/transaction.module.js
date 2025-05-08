import TransactionService from '../services/transaction.service';

const transaction = {
  namespaced: true,
  state: () => ({
    transactions: [],
    currentTransaction: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_TRANSACTIONS(state, transactions) {
      state.transactions = transactions;
    },
    SET_CURRENT_TRANSACTION(state, transaction) {
      state.currentTransaction = transaction;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchTransactions({ commit }) {
      commit('SET_LOADING', true);
      try {
        const response = await TransactionService.getAll();
        commit('SET_TRANSACTIONS', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération des transactions');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchTransaction({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await TransactionService.get(id);
        commit('SET_CURRENT_TRANSACTION', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de la transaction');
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allTransactions: state => state.transactions,
    currentTransaction: state => state.currentTransaction,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default transaction; 