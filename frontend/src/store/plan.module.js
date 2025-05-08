import PlanService from '../services/plan.service';

const plan = {
  namespaced: true,
  state: () => ({
    plans: [],
    currentPlan: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_PLANS(state, plans) {
      state.plans = plans;
    },
    SET_CURRENT_PLAN(state, plan) {
      state.currentPlan = plan;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchPlans({ commit }) {
      commit('SET_LOADING', true);
      try {
        console.log('fetchPlans');
        const response = await PlanService.getAll();
        commit('SET_PLANS', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération des plans');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchPlan({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await PlanService.get(id);
        commit('SET_CURRENT_PLAN', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération du plan');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async createPlan({ commit }, planData) {
      commit('SET_LOADING', true);
      try {
        const response = await PlanService.createPlan(planData);
        commit('ADD_PLAN', response.data);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la création du plan');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async updatePlan({ commit }, { id, planData }) {
      commit('SET_LOADING', true);
      try {
        const response = await PlanService.updatePlan(id, planData);
        commit('UPDATE_PLAN', response.data);
        return response.data;
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la mise à jour du plan');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async deletePlan({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        await PlanService.deletePlan(id);
        commit('REMOVE_PLAN', id);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la suppression du plan');
        throw error;
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allPlans: state => state.plans,
    currentPlan: state => state.currentPlan,
    loading: state => state.loading,
    error: state => state.error,
    getActivePlans: state => state.plans.filter(p => p.actif),
    getPlansSortedByPrice: state => {
      return [...state.plans].sort((a, b) => a.prix - b.prix);
    }
  }
};

export default plan; 