import PaymentService from '../services/payment.service';

const payment = {
  namespaced: true,
  state: () => ({
    paymentInfo: null,
    paymentMethods: [],
    savedMethods: [],
    loading: false,
    error: null,
    processingPayment: false,
    lastTransaction: null
  }),
  mutations: {
    SET_PAYMENT_INFO(state, info) {
      state.paymentInfo = info;
    },
    SET_PAYMENT_METHODS(state, methods) {
      state.paymentMethods = methods;
    },
    SET_SAVED_METHODS(state, methods) {
      state.savedMethods = methods;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    },
    SET_PROCESSING_PAYMENT(state, status) {
      state.processingPayment = status;
    },
    SET_LAST_TRANSACTION(state, transaction) {
      state.lastTransaction = transaction;
    },
    CLEAR_ERROR(state) {
      state.error = null;
    }
  },
  actions: {
    async payByCard({ commit }, paymentData) {
      commit('SET_LOADING', true);
      commit('CLEAR_ERROR');
      commit('SET_PROCESSING_PAYMENT', true);
      try {
        const response = await PaymentService.payByCard(paymentData);
        commit('SET_LAST_TRANSACTION', response.data);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors du paiement');
      
    }
  },
  getters: {
    paymentInfo: state => state.paymentInfo,
    paymentMethods: state => state.paymentMethods,
    savedMethods: state => state.savedMethods,
    loading: state => state.loading,
    error: state => state.error,
    processingPayment: state => state.processingPayment,
    lastTransaction: state => state.lastTransaction,
    hasError: state => !!state.error
  }
};

export default payment; 