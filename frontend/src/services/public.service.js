import axios from 'axios';

const API_URL = 'http://localhost:8081/api/test/';

// Configuration simplifiée d'Axios
const axiosInstance = axios.create({
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

class PublicService {
  getPublicContent() {
    return axiosInstance.get(API_URL + 'all');
  }
}

export default new PublicService(); 