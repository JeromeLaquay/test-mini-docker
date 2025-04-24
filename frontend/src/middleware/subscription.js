import store from '../store';

export default function subscriptionGuard(to, from, next) {
  const requiresSubscription = to.matched.some(record => record.meta.requiresSubscription);
  const hasActiveSubscription = store.getters['subscription/hasActiveSubscription'];

  if (requiresSubscription && !hasActiveSubscription) {
    next({
      path: '/subscriptions',
      query: { redirect: to.fullPath }
    });
  } else {
    next();
  }
} 