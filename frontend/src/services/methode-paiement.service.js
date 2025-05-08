import api from './api';
import router from '../router';

class MethodePaiementService {
  // Récupérer les méthodes de paiement d'un utilisateur
  async getMethodesPaiementByUser(userId) {
    const user = JSON.parse(localStorage.getItem('user'));
    console.log('Vérification user avant requête:', user); // Debug

    if (!user || !user.token) {
      console.error('Utilisateur non authentifié');
      router.push('/login');
      throw new Error('Authentification requise');
    }

    try {
      const response = await api.get(`/api/methodes-paiement/user/${userId}`);
      return response.data;
    } catch (error) {
      if (error.response?.status === 401) {
        localStorage.removeItem('user');
        router.push('/login');
      }
      throw error;
    }
  }

  // Récupérer une méthode de paiement par son ID
  getMethodePaiementById(id) {
    return api.get(`/api/methodes-paiement/${id}`);
  }

  // Créer une nouvelle méthode de paiement
  createMethodePaiement(methodePaiement) {
    return api.post('/api/methodes-paiement', methodePaiement);
  }

  // Mettre à jour une méthode de paiement
  updateMethodePaiement(id, methodePaiement) {
    return api.put(`/api/methodes-paiement/${id}`, methodePaiement);
  }

  // Supprimer une méthode de paiement
  deleteMethodePaiement(id) {
    return api.delete(`/api/methodes-paiement/${id}`);
  }

  // Initialiser un paiement
  initializePayment(paymentData) {
    return api.post('/api/methodes-paiement/initialize', paymentData);
  }
}

export default new MethodePaiementService(); 