import axios from 'axios';

const API_URL = '/api/payments';

class PaymentService {
  payWithPaypal(amount) {
    return axios.post(`${API_URL}/paypal`, null, { params: { amount } });
  }

  getPaymentHistory() {
    return axios.get(`${API_URL}/history`);
  }

  // Ces méthodes sont pour la redirection/callback, elles peuvent être utilisées si besoin
  paymentSuccess(paymentId, payerId) {
    return axios.get(`${API_URL}/success`, { params: { paymentId, PayerID: payerId } });
  }

  paymentCancel() {
    return axios.get(`${API_URL}/cancel`);
  }
}

export default new PaymentService(); 