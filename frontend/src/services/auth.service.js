import axios from 'axios';

const AUTH_API_URL = 'http://localhost:8081/api/auth/';

// Configuration Axios avec plus d'options pour le debugging
const axiosInstance = axios.create({
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  validateStatus: status => {
    return status >= 200 && status < 500; // Accepte tous les codes 2xx, 3xx et 4xx
  }
});

class AuthService {
  login(user) {
    console.log('Tentative de connexion pour l\'utilisateur:', user.username);
    return axiosInstance
      .post(AUTH_API_URL + 'signin', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        console.log('Réponse du serveur:', {
          status: response.status,
          headers: response.headers,
          data: response.data
        });
        
        if (response.data && response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
          console.log('Token stocké avec succès');
          return response.data;
        } else {
          console.warn('Réponse sans token:', response.data);
          throw new Error('Réponse invalide du serveur: token manquant');
        }
      })
      .catch(error => {
        // Log détaillé de l'erreur
        console.error('Erreur détaillée:', {
          name: error.name,
          message: error.message,
          stack: error.stack,
          isAxiosError: error.isAxiosError,
          response: error.response ? {
            status: error.response.status,
            data: error.response.data,
            headers: error.response.headers
          } : 'Pas de réponse',
          request: error.request ? {
            method: error.request.method,
            url: error.request.url,
            headers: error.request.headers
          } : 'Pas de requête',
          config: error.config ? {
            url: error.config.url,
            method: error.config.method,
            headers: error.config.headers,
            timeout: error.config.timeout
          } : 'Pas de config'
        });

        if (error.response) {
          // Erreur avec réponse du serveur
          switch (error.response.status) {
            case 401:
              throw new Error('Identifiants incorrects');
            case 404:
              throw new Error('Service d\'authentification non disponible');
            case 500:
              throw new Error('Erreur serveur. Veuillez réessayer plus tard.');
            default:
              throw new Error(`Erreur ${error.response.status}: ${error.response.data.message || 'Erreur inconnue'}`);
          }
        } else if (error.request) {
          // Pas de réponse reçue
          if (error.code === 'ECONNABORTED') {
            throw new Error('La requête a pris trop de temps. Veuillez réessayer.');
          }
          throw new Error('Le serveur ne répond pas. Veuillez vérifier votre connexion et réessayer.');
        } else {
          // Erreur de configuration de la requête
          throw new Error('Erreur de configuration de la requête: ' + error.message);
        }
      });
  }

  logout() {
    console.log('Déconnexion...');
    localStorage.removeItem('user');
  }

  register(user) {
    console.log('Tentative d\'inscription pour:', user.username);
    return axiosInstance
      .post(AUTH_API_URL + 'signup', {
        username: user.username,
        email: user.email,
        password: user.password
      })
      .then(response => {
        console.log('Inscription réussie:', response.data);
        // Si l'inscription réussit, on connecte directement l'utilisateur
        return this.login(user);
      })
      .catch(error => {
        console.error('Erreur d\'inscription:', {
          message: error.message,
          response: error.response ? {
            status: error.response.status,
            data: error.response.data
          } : 'Pas de réponse'
        });

        if (error.response) {
          if (error.response.status === 400) {
            const errorMessage = error.response.data || 'Nom d\'utilisateur ou email déjà utilisé';
            throw new Error(errorMessage);
          } else if (error.response.status === 500) {
            throw new Error('Erreur serveur. Veuillez réessayer plus tard.');
          }
        }
        throw new Error('Erreur lors de l\'inscription. Veuillez réessayer.');
      });
  }

  getCurrentUser() {
    const userStr = localStorage.getItem('user');
    if (!userStr) return null;
    return JSON.parse(userStr);
  }

  isAuthenticated() {
    const user = this.getCurrentUser();
    return user !== null && user.token !== undefined;
  }
}

export default new AuthService(); 