import axios from 'axios';

const API_URL = 'http://localhost:8081/api/auth/';

// Configuration Axios simplifiée pour éviter les erreurs CORS
const axiosInstance = axios.create({
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
});

class AuthService {
  login(user) {
    console.log('Tentative de connexion pour l\'utilisateur:', user.username);
    return axiosInstance
      .post(API_URL + 'login', {
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
          throw new Error('Réponse invalide du serveur');
        }
      })
      .catch(error => {
        console.error('Détails de l\'erreur:', {
          message: error.message,
          response: error.response ? {
            status: error.response.status,
            data: error.response.data,
            headers: error.response.headers
          } : 'Pas de réponse',
          config: {
            url: error.config.url,
            method: error.config.method,
            headers: error.config.headers
          }
        });

        if (error.response) {
          if (error.response.status === 401) {
            throw new Error('Identifiants incorrects');
          } else if (error.response.status === 500) {
            if (error.response.data && error.response.data.message && 
                error.response.data.message.includes('Query did not return a unique result')) {
              console.error('Erreur de données multiples détectée');
              throw new Error('Erreur technique: Plusieurs comptes détectés. Contactez l\'administrateur.');
            }
            throw new Error('Erreur serveur. Veuillez réessayer plus tard.');
          }
        }
        throw new Error('Erreur de connexion. Veuillez vérifier votre connexion internet.');
      });
  }

  logout() {
    console.log('Déconnexion...');
    localStorage.removeItem('user');
  }

  register(user) {
    console.log('Tentative d\'inscription pour:', user.username);
    return axiosInstance
      .post(API_URL + 'signup', {
        username: user.username,
        email: user.email,
        password: user.password
      })
      .then(response => {
        console.log('Inscription réussie:', response.data);
        return response.data;
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
            throw new Error('Nom d\'utilisateur ou email déjà utilisé');
          } else if (error.response.status === 500) {
            throw new Error('Erreur serveur. Veuillez réessayer plus tard.');
          }
        }
        throw new Error('Erreur lors de l\'inscription. Veuillez réessayer.');
      });
  }
}

export default new AuthService(); 