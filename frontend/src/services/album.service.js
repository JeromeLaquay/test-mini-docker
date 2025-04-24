import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8083/api/albums';

class AlbumService {
  async saveAlbum(coverData) {
    console.log(coverData);
    return axios.post(`${API_URL}/upload`, coverData, { headers: authHeader() });
  }

  async getUserAlbums() {
    return axios.get(`${API_URL}/user`, { headers: authHeader() });
  }

  async getAlbumById(id) {
    return axios.get(`${API_URL}/${id}`, { headers: authHeader() });
  }

  async deleteAlbum(id) {
    return axios.delete(`${API_URL}/${id}`, { headers: authHeader() });
  }

  async updateAlbum(id, coverData) {
    return axios.put(`${API_URL}/${id}`, coverData, { headers: authHeader() });
  }
}

export default new AlbumService(); 