import AlbumService from '../services/album.service';

const album = {
  namespaced: true,
  state: () => ({
    albums: [],
    currentAlbum: null,
    loading: false,
    error: null
  }),
  mutations: {
    SET_ALBUMS(state, albums) {
      state.albums = albums;
    },
    SET_CURRENT_ALBUM(state, album) {
      state.currentAlbum = album;
    },
    SET_LOADING(state, status) {
      state.loading = status;
    },
    SET_ERROR(state, error) {
      state.error = error;
    }
  },
  actions: {
    async fetchAlbums({ commit }) {
      commit('SET_LOADING', true);
      try {
        const response = await AlbumService.getAll();
        commit('SET_ALBUMS', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération des albums');
      } finally {
        commit('SET_LOADING', false);
      }
    },
    async fetchAlbum({ commit }, id) {
      commit('SET_LOADING', true);
      try {
        const response = await AlbumService.get(id);
        commit('SET_CURRENT_ALBUM', response.data);
      } catch (error) {
        commit('SET_ERROR', error.response?.data?.message || 'Erreur lors de la récupération de l\'album');
      } finally {
        commit('SET_LOADING', false);
      }
    }
  },
  getters: {
    allAlbums: state => state.albums,
    currentAlbum: state => state.currentAlbum,
    loading: state => state.loading,
    error: state => state.error
  }
};

export default album; 