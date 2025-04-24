import albumService from '../services/album.service';

const state = {
  status: '',
  error: null,
  currentAlbum: null,
  albums: []
};

const getters = {
  status: (state) => state.status,
  error: (state) => state.error,
  currentAlbum: (state) => state.currentAlbum,
  albums: (state) => state.albums
};

const actions = {
  async saveAlbumToStore({ commit }, coverData) {
    try {
      commit('savingAlbum');
      const response = await albumService.saveAlbum(coverData);
      commit('saveAlbumSuccess', response.data);
      return response.data;
    } catch (error) {
      commit('saveAlbumFailure', error.message);
      throw error;
    }
  },

  async fetchUserAlbums({ commit }) {
    try {
      commit('fetchingAlbums');
      const response = await albumService.getUserAlbums();
      commit('fetchAlbumsSuccess', response.data);
      return Promise.resolve(response.data);
    } catch (error) {
      commit('fetchAlbumsFailure', error);
      return Promise.reject(error);
    }
  },

  async getAlbum({ commit }, id) {
    try {
      commit('gettingAlbum');
      const response = await albumService.getAlbumById(id);
      commit('getAlbumSuccess', response.data);
      return Promise.resolve(response.data);
    } catch (error) {
      commit('getAlbumFailure', error);
      return Promise.reject(error);
    }
  },

  async deleteAlbum({ commit }, id) {
    try {
      await albumService.deleteAlbum(id);
      commit('deleteAlbumSuccess', id);
      return Promise.resolve();
    } catch (error) {
      commit('deleteAlbumFailure', error);
      return Promise.reject(error);
    }
  },

  async updateAlbum({ commit }, { id, coverData }) {
    try {
      commit('updatingAlbum');
      const response = await albumService.updateAlbum(id, coverData);
      commit('updateAlbumSuccess', response.data);
      return Promise.resolve(response.data);
    } catch (error) {
      commit('updateAlbumFailure', error);
      return Promise.reject(error);
    }
  }
};

const mutations = {
  savingAlbum(state) {
    state.status = 'saving';
    state.error = null;
  },
  saveAlbumSuccess(state, album) {
    state.status = 'success';
    state.currentAlbum = album;
    state.albums.push(album);
  },
  saveAlbumFailure(state, error) {
    state.status = 'error';
    state.error = error;
  },

  fetchingAlbums(state) {
    state.status = 'loading';
    state.error = null;
  },
  fetchAlbumsSuccess(state, albums) {
    state.status = 'success';
    state.albums = albums;
  },
  fetchAlbumsFailure(state, error) {
    state.status = 'error';
    state.error = error;
  },

  gettingAlbum(state) {
    state.status = 'loading';
    state.error = null;
  },
  getAlbumSuccess(state, album) {
    state.status = 'success';
    state.currentAlbum = album;
  },
  getAlbumFailure(state, error) {
    state.status = 'error';
    state.error = error;
  },

  deleteAlbumSuccess(state, id) {
    state.status = 'success';
    state.albums = state.albums.filter(album => album.id !== id);
    if (state.currentAlbum && state.currentAlbum.id === id) {
      state.currentAlbum = null;
    }
  },
  deleteAlbumFailure(state, error) {
    state.status = 'error';
    state.error = error;
  },

  updatingAlbum(state) {
    state.status = 'loading';
    state.error = null;
  },
  updateAlbumSuccess(state, updatedAlbum) {
    state.status = 'success';
    const index = state.albums.findIndex(album => album.id === updatedAlbum.id);
    if (index !== -1) {
      state.albums.splice(index, 1, updatedAlbum);
    }
    state.currentAlbum = updatedAlbum;
  },
  updateAlbumFailure(state, error) {
    state.status = 'error';
    state.error = error;
  }
};

export const album = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}; 