import api from './api';

class AlbumService {
  async getAll() {
    return api.get(`/api/albums`);
  }

  async get(id) {
    return api.get(`/api/albums/${id}`);
  }

  createAlbum(album) {
    return api.post(`/api/albums`, album);
  }

  updateAlbum(id, album) {
    return api.put(`/api/albums/${id}`, album);
  }

  deleteAlbum(id) {
    return api.delete(`/api/albums/${id}`);
  }

  getAlbumsByUserId(userId) {
    console.log("Appel de getAlbumsByUserId avec userId:", userId);
    return api.get(`/api/albums/user/${userId}`);
  }
}

export default new AlbumService(); 