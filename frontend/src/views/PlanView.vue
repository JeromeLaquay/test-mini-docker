<template>
  <div class="plan-view min-h-screen bg-gradient-to-br from-purple-900 via-indigo-900 to-blue-900 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-7xl mx-auto">
      <!-- En-tête -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-extrabold text-white sm:text-5xl">
          <span class="block">Nos Plans d'Abonnement</span>
        </h1>
        <p class="mt-3 max-w-md mx-auto text-base text-white/70 sm:text-lg md:mt-5 md:text-xl md:max-w-3xl">
          Choisissez le plan qui correspond le mieux à vos besoins
        </p>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center min-h-[400px]">
        <div class="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-purple-500"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="text-center p-4 rounded-lg bg-red-500/20 border border-red-500/30">
        <p class="text-red-200">{{ error }}</p>
      </div>

      <!-- Plans Grid -->
      <div v-else class="grid gap-6 lg:gap-8 sm:grid-cols-2 lg:grid-cols-3">
        <div 
          v-for="plan in sortedPlans" 
          :key="plan.id"
          class="bg-white/10 backdrop-blur-md rounded-2xl shadow-xl border border-white/20 p-8 relative overflow-hidden transition-transform hover:scale-105"
          :class="{'ring-2 ring-purple-500': plan.actif}"
        >
          <!-- Badge "Actif" -->
          <div 
            v-if="plan.actif"
            class="absolute top-4 right-4 bg-purple-500 text-white px-3 py-1 rounded-full text-sm font-medium"
          >
            Actif
          </div>

          <!-- En-tête du plan -->
          <div class="text-center">
            <h3 class="text-2xl font-bold text-white mb-2">{{ plan.nom }}</h3>
            
            <!-- Prix Mensuel -->
            <div class="flex justify-center items-baseline mb-2">
              <span class="text-4xl font-extrabold text-white">{{ formatPrice(plan.prixMensuel) }}€</span>
              <span class="text-white/70 ml-1">/mois</span>
            </div>

            <!-- Prix Annuel si disponible -->
            <div v-if="plan.prixAnnuel" class="flex justify-center items-baseline mb-4">
              <span class="text-2xl font-bold text-purple-400">{{ formatPrice(plan.prixAnnuel) }}€</span>
              <span class="text-white/70 ml-1">/an</span>
            </div>

            <p class="text-white/70 mb-6">{{ plan.description }}</p>
          </div>

          <!-- Période d'essai -->
          <div v-if="plan.periodeEssaiJours" class="mb-4 text-center">
            <span class="text-purple-400 font-medium">
              {{ plan.periodeEssaiJours }} jours d'essai gratuit
            </span>
          </div>

          <!-- Informations supplémentaires -->
          <div class="text-sm text-white/60 mb-6">
            <p>Date de création: {{ formatDate(plan.dateCreation) }}</p>
            <p v-if="plan.dateMiseAJour">
              Dernière mise à jour: {{ formatDate(plan.dateMiseAJour) }}
            </p>
          </div>

          <!-- Bouton de souscription -->
          <div class="mt-auto">
            <button
              @click="handleSubscribe(plan)"
              class="w-full bg-purple-600 text-white rounded-lg px-4 py-2 font-medium hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition-colors duration-200"
              :disabled="loading || !plan.actif"
            >
              {{ getButtonText(plan) }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'PlanView',
  
  computed: {
    ...mapGetters('plan', ['allPlans', 'loading', 'error']),
    ...mapGetters('auth', ['isLoggedIn']),
    
    sortedPlans() {
      return [...this.allPlans].sort((a, b) => a.prixMensuel - b.prixMensuel);
    }
  },

  created() {
    this.fetchPlans();
  },

  methods: {
    async fetchPlans() {
      try {
        await this.$store.dispatch('plan/fetchPlans');
      } catch (error) {
        console.error('Erreur lors de la récupération des plans:', error);
      }
    },

    handleSubscribe(plan) {
      if (!plan.actif) {
        return;
      }

      if (!this.isLoggedIn) {
        localStorage.setItem('selectedPlan', JSON.stringify(plan));
        this.$router.push('/login');
        return;
      }

      // Stocker le plan sélectionné et rediriger vers la page de paiement
      localStorage.setItem('selectedPlan', JSON.stringify(plan));
      this.$router.push('/plan-selected');
    },

    formatPrice(price) {
      return Number(price).toFixed(2);
    },

    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return new Intl.DateTimeFormat('fr-FR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      }).format(date);
    },

    getButtonText(plan) {
      if (!plan.actif) return 'Plan non disponible';
      if (this.loading) return 'Chargement...';
      if (!this.isLoggedIn) return 'Se connecter pour souscrire';
      return 'Souscrire';
    }
  }
};
</script>

<style scoped>
.plan-view {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style>
