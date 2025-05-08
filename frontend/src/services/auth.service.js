import api from './api';

class AuthService {
  async login(username, password) {
    try {
      const response = await api.post('/api/auth/signin', {
        username,
        password
      });
      
      console.log('Réponse login:', response.data); // Debug

      if (response.data) {
        // Standardisation des données utilisateur
        const userData = {
          id: response.data.id,
          username: response.data.username,
          email: response.data.email,
          roles: response.data.roles,
          token: response.data.token || response.data.accessToken,
          // Assurez-vous que tous les champs nécessaires sont inclus
        };

        console.log('Données à stocker:', userData); // Debug
        localStorage.setItem('user', JSON.stringify(userData));
        
        // Vérification immédiate
        const stored = localStorage.getItem('user');
        console.log('Données stockées:', stored);
      }

      return response.data;
    } catch (error) {
      console.error('Erreur login:', error);
      throw error;
    }
  }

  async signup(userData) {
    return api.post('/api/auth/signup', userData);
  }

  logout() {
    localStorage.removeItem('user');
  }

  getCurrentUser() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      return JSON.parse(userStr);
    }
    return null;
  }

  isAuthenticated() {
    const user = this.getCurrentUser();
    return user !== null && user.token !== undefined;
  }
}

export default new AuthService(); 