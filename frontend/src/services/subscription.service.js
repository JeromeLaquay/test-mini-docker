import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8082/api';

class SubscriptionService {
  // Récupérer tous les plans d'abonnement disponibles
  getPlans() {
    return axios.get(`${API_URL}/plans`, { headers: authHeader() });
  }

  // Récupérer l'abonnement actuel de l'utilisateur
  getCurrentSubscription() {
    return axios.get(`${API_URL}/subscriptions/current`, { headers: authHeader() });
  }

  // S'abonner à un plan
  subscribe(planId) {
    return axios.post(`${API_URL}/subscriptions`, { planId }, { headers: authHeader() });
  }

  // Annuler l'abonnement actuel
  cancelSubscription() {
    return axios.delete(`${API_URL}/subscriptions/current`, { headers: authHeader() });
  }

  // Récupérer l'historique des paiements
  getPaymentHistory() {
    return axios.get(`${API_URL}/subscriptions/history`, { headers: authHeader() });
  }
}

export default new SubscriptionService(); 