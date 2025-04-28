import PaymentService from '@/services/payment.service';

const state = () => ({
  paymentUrl: '',
  paymentError: '',
  paymentHistory: [],
  paymentStatus: null // 'success' | 'cancel' | null
});

const mutations = {
  SET_PAYMENT_URL(state, url) {
    state.paymentUrl = url;
  },
  SET_PAYMENT_ERROR(state, error) {
    state.paymentError = error;
  },
  SET_PAYMENT_HISTORY(state, history) {
    state.paymentHistory = history;
  },
  SET_PAYMENT_STATUS(state, status) {
    state.paymentStatus = status;
  }
};

const actions = {
  async payWithPaypal({ commit }, amount) {
    commit('SET_PAYMENT_ERROR', '');
    try {
      const res = await PaymentService.payWithPaypal(amount);
      commit('SET_PAYMENT_URL', res.data.approval_url);
      return res.data.approval_url;
    } catch (err) {
      commit('SET_PAYMENT_ERROR', err.response?.data?.message || err.message);
      throw err;
    }
  },
  async fetchPaymentHistory({ commit }) {
    try {
      const res = await PaymentService.getPaymentHistory();
      commit('SET_PAYMENT_HISTORY', res.data);
    } catch (err) {
      commit('SET_PAYMENT_ERROR', err.response?.data?.message || err.message);
    }
  },
  async paymentSuccess({ commit }, { paymentId, payerId }) {
    try {
      await PaymentService.paymentSuccess(paymentId, payerId);
      commit('SET_PAYMENT_STATUS', 'success');
    } catch (err) {
      commit('SET_PAYMENT_ERROR', err.response?.data?.message || err.message);
    }
  },
  async paymentCancel({ commit }) {
    try {
      await PaymentService.paymentCancel();
      commit('SET_PAYMENT_STATUS', 'cancel');
    } catch (err) {
      commit('SET_PAYMENT_ERROR', err.response?.data?.message || err.message);
    }
  }
};

const getters = {
  paymentUrl: state => state.paymentUrl,
  paymentError: state => state.paymentError,
  paymentHistory: state => state.paymentHistory,
  paymentStatus: state => state.paymentStatus
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}; 