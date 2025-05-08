import api from './api';

class FactureService {
  async getAll() {
    return api.get(`/api/factures`);
  }

  async get(id) {
    return api.get(`/api/factures/${id}`);
  }

  getFacturesByUser(userId) {
    return api.get(`/api/factures/user/${userId}`);
  }

  createFacture(factureData) {
    return api.post(`/api/factures`, factureData);
  }

  updateFacture(id, factureData) {
    return api.put(`/api/factures/${id}`, factureData);
  }

  deleteFacture(id) {
    return api.delete(`/api/factures/${id}`);
  }
}

export default new FactureService(); 