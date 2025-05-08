import api from './api';

class TransactionService {
  async getAll() {
    return api.get(`/api/transactions`);
  }

  async get(id) {
    return api.get(`/api/transactions/${id}`);
  }

  getTransactionById(id) {
    return api.get(`/api/transactions/${id}`);
  }

  getTransactionsByUser(userId) {
    return api.get(`/api/transactions/user/${userId}`);
  }

  createTransaction(transactionData) {
    return api.post(`/api/transactions`, transactionData);
  }

  updateTransaction(id, transactionData) {
    return api.put(`/api/transactions/${id}`, transactionData);
  }

  deleteTransaction(id) {
    return api.delete(`/api/transactions/${id}`);
  }

  getTransactionsByStatus(status) {
    return api.get(`/api/transactions/status/${status}`);
  }
}

export default new TransactionService(); 