import { createStore } from 'vuex';
import auth from './auth.module';
import user from './user.module';
import payment from './payment.module';
import subscription from './subscription.module';
import album from './album.module';
import transaction from './transaction.module';
import facture from './facture.module';
import historique from './historique.module';
import plan from './plan.module';
import methodePaiement from './methode-paiement.module';

const store = createStore({
  modules: {
    auth,
    user,
    payment,
    subscription,
    album,
    transaction,
    facture,
    historique,
    plan,
    methodePaiement
  }
});

export default store; 