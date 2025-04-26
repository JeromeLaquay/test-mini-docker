import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8082/api/albums/';

class AlbumService {
  getAllAlbums() {
    return axios.get(API_URL + 'all', { headers: authHeader() });
  }

  getAlbumById(id) {
    return axios.get(API_URL + `${id}`, { headers: authHeader() });
  }

  createAlbum(album) {
    return axios.post(API_URL, album, { headers: authHeader() });
  }

  updateAlbum(id, album) {
    return axios.put(API_URL + `${id}`, album, { headers: authHeader() });
  }

  deleteAlbum(id) {
    return axios.delete(API_URL + `${id}`, { headers: authHeader() });
  }

  getAlbumsByUserId(userId) {
    console.log("Appel de getAlbumsByUserId avec userId:", userId);
    return axios.get(API_URL + `user/${userId}`, { headers: authHeader() });
  }
}

export default new AlbumService(); 