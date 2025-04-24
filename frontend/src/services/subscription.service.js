import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8084/api';
const MAX_RETRIES = 3;
const RETRY_DELAY = 1000;

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

class SubscriptionService {
  async retryRequest(requestFn, retries = MAX_RETRIES) {
    for (let i = 0; i < retries; i++) {
      try {
        return await requestFn();
      } catch (error) {
        console.error(`Tentative ${i + 1}/${retries} échouée:`, error);
        if (i === retries - 1) throw error;
        if (error.message === 'Network Error') {
          console.log(`Nouvelle tentative dans ${RETRY_DELAY}ms...`);
          await sleep(RETRY_DELAY);
          continue;
        }
        throw error;
      }
    }
  }

  async getCurrentSubscription() {
    console.log('Appel API getCurrentSubscription');
    return this.retryRequest(async () => {
      try {
        const response = await axios.get(`${API_URL}/subscription/current`, { 
          headers: authHeader(),
          timeout: 5000
        });
        console.log('Réponse subscription:', response.data);
        return response;
      } catch (error) {
        console.error('Erreur getCurrentSubscription:', error);
        throw error;
      }
    });
  }

  async getAvailablePlans() {
    console.log('Appel API getAvailablePlans');
    return this.retryRequest(async () => {
      try {
        const response = await axios.get(`${API_URL}/subscription/plans`);
        console.log('Réponse plans:', response.data);
        return response;
      } catch (error) {
        console.error('Erreur getAvailablePlans:', error);
        throw error;
      }
    });
  }

  async subscribe(planId) {
    console.log('Appel API subscribe avec planId:', planId);
    return this.retryRequest(async () => {
      try {
        const response = await axios.post(
          `${API_URL}/subscription/subscribe`,
          { planId },
          { 
            headers: authHeader(),
            timeout: 5000
          }
        );
        console.log('Réponse subscribe:', response.data);
        return response;
      } catch (error) {
        console.error('Erreur subscribe:', error);
        throw error;
      }
    });
  }

  async cancelSubscription() {
    console.log('Appel API cancelSubscription');
    return this.retryRequest(async () => {
      try {
        const response = await axios.post(
          `${API_URL}/subscription/cancel`,
          {},
          { 
            headers: authHeader(),
            timeout: 5000
          }
        );
        console.log('Réponse cancel:', response.data);
        return response;
      } catch (error) {
        console.error('Erreur cancelSubscription:', error);
        throw error;
      }
    });
  }

  async getSubscriptionHistory() {
    console.log('Appel API getSubscriptionHistory');
    return this.retryRequest(async () => {
      try {
        const response = await axios.get(
          `${API_URL}/subscription/history`,
          { 
            headers: authHeader(),
            timeout: 5000
          }
        );
        console.log('Réponse history:', response.data);
        return response;
      } catch (error) {
        console.error('Erreur getSubscriptionHistory:', error);
        throw error;
      }
    });
  }

  async updatePaymentMethod(paymentMethodData) {
    const response = await axios.put(
      `${API_URL}/subscription/payment-method`,
      paymentMethodData,
      { headers: authHeader() }
    );
    return response.data;
  }
}

export default new SubscriptionService(); 