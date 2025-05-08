import api from './api';

class SubscriptionService {
  async getAll() {
    return api.get(`/api/abonnements`);
  }

  async get(id) {
    return api.get(`/api/abonnements/${id}`);
  }

  async subscribe(planId) {
    return api.post(`/api/abonnements/subscribe`, { planId });
  }

  async cancel() {
    return api.post(`/api/abonnements/cancel`, {});
  }

  // Récupérer tous les plans d'abonnement disponibles
  getPlans() {
    return api.get(`/api/abonnements/plans`);
  }

  // Récupérer l'abonnement actuel de l'utilisateur
  getCurrentSubscription(userId) {
    return api.get(`/api/abonnements/current/${userId}`);
  }

  // Récupérer l'historique des paiements
  getPaymentHistory() {
    return api.get(`/api/abonnements/payment-history`);
  }

  getAbonnementsByUser(userId) {
    return api.get(`/api/abonnements/user/${userId}`);
  }

  createAbonnement(abonnementData) {
    return api.post(`/api/abonnements`, abonnementData);
  }

  updateAbonnement(id, abonnementData) {
    return api.put(`/api/abonnements/${id}`, abonnementData);
  }

  cancelAbonnement(id) {
    return api.put(`/api/abonnements/${id}/cancel`, {});
  }

  suspendAbonnement(id) {
    return api.put(`/api/abonnements/${id}/suspend`, {});
  }

  reactivateAbonnement(id) {
    return api.put(`/api/abonnements/${id}/reactivate`, {});
  }

  getAbonnementsByStatus(status) {
    return api.get(`/api/abonnements/status/${status}`);
  }
}

export default new SubscriptionService(); 