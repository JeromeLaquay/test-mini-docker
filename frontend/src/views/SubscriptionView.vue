<template>
  <div class="container mx-auto px-4 py-8">
    <!-- Section Plans d'abonnement -->
    <section class="mb-12">
      <h2 class="text-3xl font-bold mb-6 text-gray-800">Plans d'abonnement</h2>
      <div v-if="loading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-purple-600"></div>
      </div>
      <div v-else-if="error" class="text-center py-12">
        <p class="text-red-600">{{ error }}</p>
        <button @click="loadPlans" class="mt-4 px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700">
          Réessayer
        </button>
      </div>
      <div v-else class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div v-for="plan in plans" :key="plan.id" 
             class="bg-white rounded-lg shadow-lg p-6 border border-gray-200 transform transition-transform duration-300 hover:scale-105">
          <div class="relative">
            <h3 class="text-xl font-semibold mb-2 text-purple-600">{{ plan.name }}</h3>
            <p class="text-gray-600 mb-4 h-20">{{ plan.description }}</p>
            <div class="text-3xl font-bold mb-4 text-gray-800">
              {{ formatPrice(plan.price) }} €
              <span class="text-sm text-gray-500 font-normal">/mois</span>
            </div>
            <ul class="mb-6 text-gray-700 space-y-3">
              <li class="flex items-center">
                <svg class="w-5 h-5 text-purple-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                {{ plan.maxAlbums }} albums maximum
              </li>
              <li class="flex items-center">
                <svg class="w-5 h-5 text-purple-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                Durée : {{ plan.durationInDays }} jours
              </li>
            </ul>
            <button @click="subscribeToPlan(plan.id)"
                    :disabled="isCurrentPlan(plan.id)"
                    class="w-full py-3 px-4 rounded-lg bg-purple-600 text-white hover:bg-purple-700 disabled:opacity-50 disabled:cursor-not-allowed transform transition-transform duration-200 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-opacity-50">
              <span v-if="isCurrentPlan(plan.id)">Plan actuel</span>
              <span v-else>Choisir ce plan</span>
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- Section Abonnement actuel -->
    <section v-if="currentSubscription" class="mb-12">
      <h2 class="text-2xl font-bold mb-4">Votre abonnement actuel</h2>
      <div class="bg-white rounded-lg shadow p-6 border border-gray-200">
        <div class="flex justify-between items-center mb-4">
          <div>
            <h3 class="text-xl font-semibold">{{ getPlanName(currentSubscription.planId) }}</h3>
            <p class="text-gray-600">
              Albums créés : {{ currentSubscription.albumsCreated }} / {{ getCurrentPlan()?.maxAlbums }}
            </p>
          </div>
          <button @click="cancelCurrentSubscription"
                  class="py-2 px-4 rounded-md bg-red-600 text-white hover:bg-red-700">
            Annuler l'abonnement
          </button>
        </div>
        <div class="mt-4">
          <div class="h-2 bg-gray-200 rounded-full">
            <div class="h-2 bg-purple-600 rounded-full"
                 :style="{ width: `${(currentSubscription.albumsCreated / getCurrentPlan()?.maxAlbums) * 100}%` }">
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Section Historique -->
    <section v-if="subscriptionHistory && subscriptionHistory.length > 0" class="mb-12">
      <h2 class="text-2xl font-bold mb-4">Historique des abonnements</h2>
      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Plan</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date de début</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date de fin</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Statut</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="subscription in subscriptionHistory" :key="subscription.id">
              <td class="px-6 py-4 whitespace-nowrap">{{ getPlanName(subscription.planId) }}</td>
              <td class="px-6 py-4 whitespace-nowrap">{{ formatDate(subscription.startDate) }}</td>
              <td class="px-6 py-4 whitespace-nowrap">{{ formatDate(subscription.endDate) }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="getStatusClass(subscription.status)">
                  {{ subscription.status }}
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>

<script>
import { onMounted, computed } from 'vue';
import { useToast } from 'vue-toastification';
import { useStore } from 'vuex';

export default {
  name: 'SubscriptionView',
  setup() {
    const store = useStore();
    const toast = useToast();

    // Computed properties from store
    const plans = computed(() => store.state.subscription.plans);
    const loading = computed(() => store.state.subscription.loading);
    const error = computed(() => store.state.subscription.error);
    const currentSubscription = computed(() => store.state.subscription.currentSubscription);
    const subscriptionHistory = computed(() => store.state.subscription.paymentHistory);

    const loadPlans = async () => {
      try {
        console.log("Chargement des plans...");
        await store.dispatch('subscription/fetchPlans');
        console.log("Plans chargés:", plans.value);
      } catch (error) {
        console.error("Erreur loadPlans:", error);
        toast.error("Erreur lors du chargement des plans");
      }
    };

    const loadCurrentSubscription = async () => {
      try {
        await store.dispatch('subscription/fetchCurrentSubscription');
      } catch (error) {
        if (error.response?.status !== 404) {
          toast.error("Erreur lors du chargement de l'abonnement actuel");
        }
      }
    };

    const loadSubscriptionHistory = async () => {
      try {
        await store.dispatch('subscription/fetchPaymentHistory');
      } catch (error) {
        toast.error("Erreur lors du chargement de l'historique");
      }
    };

    const subscribeToPlan = async (planId) => {
      try {
        await store.dispatch('subscription/subscribe', planId);
        toast.success("Abonnement souscrit avec succès");
      } catch (error) {
        toast.error(error.response?.data?.message || "Erreur lors de la souscription");
      }
    };

    const cancelCurrentSubscription = async () => {
      if (!confirm("Êtes-vous sûr de vouloir annuler votre abonnement ?")) return;
      
      try {
        await store.dispatch('subscription/cancelSubscription');
        toast.success("Abonnement annulé avec succès");
      } catch (error) {
        toast.error("Erreur lors de l'annulation de l'abonnement");
      }
    };

    const formatPrice = (price) => {
      return new Intl.NumberFormat('fr-FR', { minimumFractionDigits: 2 }).format(price);
    };

    const formatDate = (date) => {
      return new Date(date).toLocaleDateString('fr-FR');
    };

    const isCurrentPlan = (planId) => {
      return currentSubscription.value?.planId === planId;
    };

    const getPlanName = (planId) => {
      const plan = plans.value.find(p => p.id === planId);
      return plan?.name || 'Plan inconnu';
    };

    const getCurrentPlan = () => {
      return plans.value.find(p => p.id === currentSubscription.value?.planId);
    };

    const getStatusClass = (status) => {
      const classes = {
        'ACTIVE': 'bg-green-100 text-green-800',
        'CANCELLED': 'bg-red-100 text-red-800',
        'EXPIRED': 'bg-gray-100 text-gray-800'
      };
      return `px-2 py-1 text-xs font-medium rounded-full ${classes[status] || ''}`;
    };

    onMounted(async () => {
      console.log("Composant monté, chargement des données...");
      await loadPlans();
      await loadCurrentSubscription();
      await loadSubscriptionHistory();
    });

    return {
      plans,
      currentSubscription,
      subscriptionHistory,
      loading,
      error,
      subscribeToPlan,
      cancelCurrentSubscription,
      formatPrice,
      formatDate,
      isCurrentPlan,
      getPlanName,
      getCurrentPlan,
      getStatusClass
    };
  }
};
</script> 