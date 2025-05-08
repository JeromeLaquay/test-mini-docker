import api from './api';

class UserService {
  async getAll() {
    return api.get(`/api/users`);
  }

  async get(id) {
    return api.get(`/api/users/${id}`);
  }

  async update(id, userData) {
    return api.put(`/api/users/${id}`, userData);
  }
}

export default new UserService(); 