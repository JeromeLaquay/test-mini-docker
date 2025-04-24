import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import './assets/tailwind.css'

// Intercepteurs Axios pour le débogage
axios.interceptors.request.use(
  config => {
    console.log(`[Axios] Requête ${config.method.toUpperCase()} vers ${config.url}`, config.headers);
    return config;
  },
  error => {
    console.error('[Axios] Erreur de requête:', error);
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  response => {
    console.log(`[Axios] Réponse de ${response.config.url}:`, response.status);
    return response;
  },
  error => {
    console.error('[Axios] Erreur de réponse:', error.response ? error.response.status : 'Pas de réponse', 
                 error.response ? error.response.data : error.message);
    return Promise.reject(error);
  }
);

createApp(App).use(store).use(router).mount('#app') 