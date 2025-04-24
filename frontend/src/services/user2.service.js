import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8081/api/test/';

// Configuration simplifiée d'Axios
const axiosInstance = axios.create({
  // Sans withCredentials pour éviter problèmes CORS
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

class UserService {
  getPublicContent() {
    return axiosInstance.get(API_URL + 'all');
  }

  getUserBoard() {
    return axiosInstance.get(API_URL + 'user', { headers: authHeader() });
  }

  getModeratorBoard() {
    return axiosInstance.get(API_URL + 'mod', { headers: authHeader() });
  }

  getAdminBoard() {
    return axiosInstance.get(API_URL + 'admin', { headers: authHeader() });
  }
}

export default new UserService(); 