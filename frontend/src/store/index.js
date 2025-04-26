import { createStore } from 'vuex';

import { auth } from './auth.module';
import { user } from './user.module';
import { album } from './album.module';

export default createStore({
  modules: {
    auth,
    user,
    album
  }
}); 