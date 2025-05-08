import api from './api';

class PlanService {
  async getAll() {
    return api.get(`/api/plans`);
  }

  async get(id) {
    return api.get(`/api/plans/${id}`);
  }

  async create(planData) {
    return api.post(`/api/plans`, planData);
  }

  async update(id, planData) {
    return api.put(`/api/plans/${id}`, planData);
  }

  async delete(id) {
    return api.delete(`/api/plans/${id}`);
  }

  getActivePlans() {
    return api.get(`/api/plans/active`);
  }
}

export default new PlanService(); 