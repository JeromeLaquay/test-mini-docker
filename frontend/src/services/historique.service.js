import api from './api';

class HistoriqueService {
  async getAll() {
    return api.get(`/api/historiques`);
  }

  async get(id) {
    return api.get(`/api/historiques/${id}`);
  }

  getHistoriqueByUser(userId) {
    return api.get(`/api/historiques/user/${userId}`);
  }

  createHistorique(historiqueData) {
    return api.post(`/api/historiques`, historiqueData);
  }

  getHistoriqueByType(type) {
    return api.get(`/api/historiques/type/${type}`);
  }

  getHistoriqueByDateRange(debut, fin) {
    return api.get(`/api/historiques/periode`, {
      params: { debut, fin },
    });
  }
}

export default new HistoriqueService(); 