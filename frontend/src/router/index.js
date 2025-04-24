import { createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import des vues
import MusicUploadView from '../views/MusicUploadView.vue'
//import SubscriptionPlansView from '../views/SubscriptionPlansView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import AlbumCoverCreator from '../views/AlbumCoverCreator.vue'
import ProfileView from '../views/ProfileView.vue'
import PaymentView from '../views/PaymentView.vue'
import AdminView from '../views/AdminView.vue'
import UserManagementView from '../views/UserManagementView.vue'
import SubscriptionView from '../views/SubscriptionView.vue'

const routes = [
  // Routes publiques
  {
    path: '/',
    name: 'home',
    component: MusicUploadView,
    meta: { requiresAuth: false }
  },
  {
    path: '/home',
    name: 'home',
    component: MusicUploadView,
    meta: { requiresAuth: false }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: { requiresAuth: false }
  },
  
  // Routes protégées (nécessitent une authentification)
  {
    path: '/albums',
    name: 'albums',
    component: AlbumCoverCreator,
    meta: { requiresAuth: false }
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    meta: { requiresAuth: true }
  },
  {
    path: '/payment',
    name: 'payment',
    component: PaymentView,
    meta: { requiresAuth: true }
  },
  
  // Routes admin (nécessitent une authentification et un rôle admin)
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/user-management',
    name: 'user-management',
    component: UserManagementView,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/subscriptions',
    name: 'subscriptions',
    component: SubscriptionView,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const store = useStore()
  const isAuthenticated = store.getters['auth/isLoggedIn']
  const isAdmin = store.getters['auth/isAdmin']

  // Redirection vers login si authentification requise
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login', query: { redirect: to.fullPath } })
    return
  }

  // Redirection vers home si accès admin requis sans être admin
  if (to.meta.requiresAdmin && !isAdmin) {
    next({ name: 'home' })
    return
  }

  // Redirection vers home si déjà authentifié et tentative d'accès à login/register
  if (isAuthenticated && (to.name === 'login' || to.name === 'register')) {
    next({ name: 'home' })
    return
  }

  next()
})

export default router 