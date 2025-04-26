import AlbumService from '../services/album.service';

export const album = {
  namespaced: true,
  state: {
    albums: [],
    selectedAlbum: null
  },
  getters: {
    albums: state => state.albums,
    selectedAlbum: state => state.selectedAlbum
  },
  mutations: {
    SET_ALBUMS: (state, albums) => {
      state.albums = albums;
    },
    SET_SELECTED_ALBUM: (state, album) => {
      state.selectedAlbum = album;
    },
    ADD_ALBUM: (state, album) => {
      state.albums.push(album);
    },
    UPDATE_ALBUM: (state, updatedAlbum) => {
      const index = state.albums.findIndex(album => album.id === updatedAlbum.id);
      if (index !== -1) {
        state.albums.splice(index, 1, updatedAlbum);
      }
    },
    REMOVE_ALBUM: (state, albumId) => {
      state.albums = state.albums.filter(album => album.id !== albumId);
    }
  },
  actions: {
    fetchAlbums({ commit }) {
      return AlbumService.getAllAlbums()
        .then(response => {
          commit('SET_ALBUMS', response.data);
          return Promise.resolve(response.data);
        })
        .catch(error => {
          return Promise.reject(error);
        });
    },
    fetchAlbumById({ commit }, albumId) {
      return AlbumService.getAlbumById(albumId)
        .then(response => {
          commit('SET_SELECTED_ALBUM', response.data);
          return Promise.resolve(response.data);
        })
        .catch(error => {
          return Promise.reject(error);
        });
    },
    saveAlbum({ commit }, album) {
      return AlbumService.createAlbum(album)
        .then(response => {
          commit('ADD_ALBUM', response.data);
          return Promise.resolve(response.data);
        })
        .catch(error => {
          return Promise.reject(error);
        });
    },
    updateAlbum({ commit }, { id, album }) {
      return AlbumService.updateAlbum(id, album)
        .then(response => {
          commit('UPDATE_ALBUM', response.data);
          return Promise.resolve(response.data);
        })
        .catch(error => {
          return Promise.reject(error);
        });
    },
    deleteAlbum({ commit }, albumId) {
      return AlbumService.deleteAlbum(albumId)
        .then(() => {
          commit('REMOVE_ALBUM', albumId);
          return Promise.resolve();
        })
        .catch(error => {
          return Promise.reject(error);
        });
    },
    fetchUserAlbums({ commit }, userId) {
      return AlbumService.getAlbumsByUserId(userId)
        .then(response => {
          commit('SET_ALBUMS', response.data);
          return Promise.resolve(response.data);
        })
        .catch(error => {
          return Promise.reject(error);
        });
    }
  }
}; 