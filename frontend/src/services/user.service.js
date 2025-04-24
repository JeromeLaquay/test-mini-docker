import axios from 'axios';
import authHeader from './auth-header';

const API_URL = process.env.VUE_APP_API_URL;

// Configuration simplifiée d'Axios
const axiosInstance = axios.create({
  // Sans withCredentials pour éviter problèmes CORS
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

class UserManagementService {
  // Récupérer tous les utilisateurs
  getAllUsers() {
    const headers = authHeader();
    console.log('getAllUsers - Headers:', headers);
    
    return axiosInstance.get(API_URL, { headers })
      .catch(error => {
        console.error('Erreur dans getAllUsers:', error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Récupérer un utilisateur par son ID
  getUserById(id) {
    const headers = authHeader();
    console.log(`getUserById(${id}) - Headers:`, headers);
    
    return axiosInstance.get(`${API_URL}/${id}`, { headers })
      .catch(error => {
        console.error(`Erreur dans getUserById(${id}):`, error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Récupérer un utilisateur par son nom d'utilisateur
  getUserByUsername(username) {
    const headers = authHeader();
    console.log(`getUserByUsername(${username}) - Headers:`, headers);
    
    return axiosInstance.get(`${API_URL}/username/${username}`, { headers })
      .catch(error => {
        console.error(`Erreur dans getUserByUsername(${username}):`, error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Créer un nouvel utilisateur
  createUser(userData) {
    const headers = authHeader();
    console.log('createUser - Headers:', headers);
    console.log('userData:', JSON.stringify(userData));
    
    return axiosInstance.post(API_URL, userData, { headers })
      .catch(error => {
        console.error('Erreur dans createUser:', error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Mettre à jour un utilisateur
  updateUser(id, userData) {
    const headers = authHeader();
    console.log(`updateUser(${id}) - Headers:`, headers);
    console.log('userData:', JSON.stringify(userData));
    
    return axiosInstance.put(`${API_URL}/${id}`, userData, { headers })
      .catch(error => {
        console.error(`Erreur dans updateUser(${id}):`, error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Supprimer un utilisateur
  deleteUser(id) {
    const headers = authHeader();
    console.log(`deleteUser(${id}) - Headers:`, headers);
    
    return axiosInstance.delete(`${API_URL}/${id}`, { headers })
      .catch(error => {
        console.error(`Erreur dans deleteUser(${id}):`, error.response?.status, error.response?.data);
        throw error;
      });
  }

  // Récupérer le profil complet de l'utilisateur
  async getUserProfile() {
    const response = await axios.get(`${API_URL}/user/profile`, { headers: authHeader() });
    return response.data;
  }

  // Mettre à jour le profil de l'utilisateur
  async updateProfile(profileData) {
    const response = await axios.put(
      `${API_URL}/user/profile`,
      profileData,
      { headers: authHeader() }
    );
    return response.data;
  }

  // Changer le mot de passe de l'utilisateur
  async changePassword(oldPassword, newPassword) {
    const response = await axios.post(
      `${API_URL}/user/change-password`,
      { oldPassword, newPassword },
      { headers: authHeader() }
    );
    return response.data;
  }

  // Récupérer les statistiques de l'utilisateur
  async getUserStats() {
    const response = await axios.get(`${API_URL}/user/stats`, { headers: authHeader() });
    return response.data;
  }

  // Récupérer les badges de l'utilisateur
  async getUserBadges() {
    const response = await axios.get(`${API_URL}/user/badges`, { headers: authHeader() });
    return response.data;
  }

  // Récupérer les activités de l'utilisateur
  async getUserActivities() {
    const response = await axios.get(`${API_URL}/user/activities`, { headers: authHeader() });
    return response.data;
  }
}

export default new UserManagementService(); 