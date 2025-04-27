import { createStore } from 'vuex';

import { auth } from './auth.module';
import { user } from './user.module';
import { album } from './album.module';
import subscription from './subscription.module';

export default createStore({
  modules: {
    auth,
    user,
    album,
    subscription
  }
}); 