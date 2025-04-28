import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8082/api';

class SubscriptionService {
  // Récupérer tous les plans d'abonnement disponibles
  getPlans() {
    return axios.get(`${API_URL}/plans`, { headers: authHeader() });
  }

  // Récupérer l'abonnement actuel de l'utilisateur
  getCurrentSubscription(userId) {
    //return axios.get(`${API_URL}/subscriptions/current`, { headers: authHeader() });
    //return axios.get(`${API_URL}/subscriptions/user/${userId}`, { headers: authHeader() });
    console.log("Récupérer l'abonnement actuel de l'utilisateur"+userId);
    return {
      status: 200,
      message: "Abonnement actuel récupéré avec succès"
    }
  }

  // S'abonner à un plan
  subscribe(planId) {
    //return axios.post(`${API_URL}/subscriptions`, { planId }, { headers: authHeader() });
    console.log("S'abonner à un plan"+planId);
    return {
      status: 200,
      message: "Abonnement souscrit avec succès"
    }
  }

  // Annuler l'abonnement actuel
  cancelSubscription() {
    //return axios.delete(`${API_URL}/subscriptions/current`, { headers: authHeader() });
    return {
      status: 200,
      message: "Abonnement annulé avec succès"
    }
  }

  // Récupérer l'historique des paiements
  getPaymentHistory() {
    //return axios.get(`${API_URL}/subscriptions/history`, { headers: authHeader() });
    return {
      status: 200,
      message: "Historique des paiements récupéré avec succès"
    }
  }
}

export default new SubscriptionService(); 