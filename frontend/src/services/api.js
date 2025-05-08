import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Intercepteur pour les requêtes
api.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user'));
    console.log('LocalStorage user:', user); // Debug

    if (user?.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
      console.log('Request headers:', config.headers); // Debug
    } else {
      console.warn('No token found in localStorage');
    }
    
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// Intercepteur pour les réponses
api.interceptors.response.use(
  (response) => {
    console.log('Response:', response);
    return response;
  },
  async (error) => {
    if (error.response?.status === 401) {
      console.log('401 error detected, current localStorage:', localStorage.getItem('user'));
      
      // Tentative de rafraîchissement du token
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user?.refreshToken) {
          const response = await api.post('/api/auth/refresh-token', {
            refreshToken: user.refreshToken
          });
          
          if (response.data.token) {
            localStorage.setItem('user', JSON.stringify({
              ...user,
              token: response.data.token
            }));
            
            // Réessayer la requête originale
            error.config.headers.Authorization = `Bearer ${response.data.token}`;
            return api(error.config);
          }
        }
      } catch (refreshError) {
        console.error('Token refresh failed:', refreshError);
        localStorage.removeItem('user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api; 