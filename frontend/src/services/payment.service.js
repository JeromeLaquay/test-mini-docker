import api from './api';

class PaymentService {

  payByCard(paymentData) {
    return api.post('/api/payment/pay-by-card', paymentData);
  }
}

export default new PaymentService();