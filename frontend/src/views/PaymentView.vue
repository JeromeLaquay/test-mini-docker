<template>
  <div class="min-h-screen bg-gray-900 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md mx-auto bg-white rounded-lg shadow-lg overflow-hidden">
      <div class="px-6 py-8">
        <div class="text-center mb-8">
          <h2 class="text-2xl font-bold text-gray-900">Paiement</h2>
          <p class="mt-2 text-sm text-gray-600">
            Plan sélectionné : {{ selectedPlan.name }} - {{ selectedPlan.price }}€/mois
          </p>
        </div>

        <form @submit.prevent="processPayment" class="space-y-6">
          <!-- Informations de carte -->
          <div>
            <label class="block text-sm font-medium text-gray-700">
              Numéro de carte
            </label>
            <div class="mt-1 relative">
              <input
                v-model="cardNumber"
                type="text"
                maxlength="19"
                @input="formatCardNumber"
                placeholder="4242 4242 4242 4242"
                class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-purple-500 focus:border-purple-500"
                :class="{ 'border-red-500': errors.cardNumber }"
              />
              <span class="absolute right-3 top-2 text-gray-400">
                💳
              </span>
            </div>
            <p v-if="errors.cardNumber" class="mt-1 text-sm text-red-600">
              {{ errors.cardNumber }}
            </p>
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700">
                Date d'expiration
              </label>
              <input
                v-model="expiry"
                type="text"
                maxlength="5"
                @input="formatExpiry"
                placeholder="MM/YY"
                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-purple-500 focus:border-purple-500"
                :class="{ 'border-red-500': errors.expiry }"
              />
              <p v-if="errors.expiry" class="mt-1 text-sm text-red-600">
                {{ errors.expiry }}
              </p>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700">
                Code CVC
              </label>
              <input
                v-model="cvc"
                type="text"
                maxlength="3"
                placeholder="123"
                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-purple-500 focus:border-purple-500"
                :class="{ 'border-red-500': errors.cvc }"
              />
              <p v-if="errors.cvc" class="mt-1 text-sm text-red-600">
                {{ errors.cvc }}
              </p>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">
              Nom sur la carte
            </label>
            <input
              v-model="cardName"
              type="text"
              placeholder="JOHN DOE"
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-purple-500 focus:border-purple-500"
              :class="{ 'border-red-500': errors.cardName }"
            />
            <p v-if="errors.cardName" class="mt-1 text-sm text-red-600">
              {{ errors.cardName }}
            </p>
          </div>

          <div class="mt-6">
            <button
              type="submit"
              :disabled="isProcessing"
              class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
            >
              <span v-if="isProcessing">
                Traitement en cours...
              </span>
              <span v-else>
                Payer {{ selectedPlan.price }}€
              </span>
            </button>
          </div>
        </form>

        <!-- Message de succès -->
        <div v-if="paymentSuccess" class="mt-4 p-4 bg-green-100 rounded-md">
          <p class="text-sm text-green-700">
            Paiement réussi ! Redirection en cours...
          </p>
        </div>

        <!-- Message d'erreur -->
        <div v-if="paymentError" class="mt-4 p-4 bg-red-100 rounded-md">
          <p class="text-sm text-red-700">
            {{ paymentError }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'PaymentView',
  setup() {
    const store = useStore();
    const router = useRouter();

    const cardNumber = ref('');
    const expiry = ref('');
    const cvc = ref('');
    const cardName = ref('');
    const isProcessing = ref(false);
    const paymentSuccess = ref(false);
    const paymentError = ref('');
    const errors = ref({});

    const selectedPlan = computed(() => {
      const plan = store.state.subscription.selectedPlan;
      // Valeur par défaut si aucun plan n'est sélectionné
      return plan || { 
        name: 'Pro', 
        price: 9.99,
        id: 'pro'
      };
    });

    const formatCardNumber = (event) => {
      let value = event.target.value.replace(/\s/g, '').replace(/\D/g, '');
      let formattedValue = '';
      for (let i = 0; i < value.length; i++) {
        if (i > 0 && i % 4 === 0) {
          formattedValue += ' ';
        }
        formattedValue += value[i];
      }
      cardNumber.value = formattedValue;
    };

    const formatExpiry = (event) => {
      let value = event.target.value.replace(/\D/g, '');
      if (value.length >= 2) {
        value = value.slice(0, 2) + '/' + value.slice(2);
      }
      expiry.value = value;
    };

    const validateForm = () => {
      const newErrors = {};

      if (!cardNumber.value || cardNumber.value.replace(/\s/g, '').length !== 16) {
        newErrors.cardNumber = 'Numéro de carte invalide';
      }

      if (!expiry.value || !expiry.value.match(/^(0[1-9]|1[0-2])\/([0-9]{2})$/)) {
        newErrors.expiry = 'Date invalide';
      }

      if (!cvc.value || cvc.value.length !== 3) {
        newErrors.cvc = 'CVC invalide';
      }

      if (!cardName.value) {
        newErrors.cardName = 'Nom requis';
      }

      errors.value = newErrors;
      return Object.keys(newErrors).length === 0;
    };

    const processPayment = async () => {
      if (!validateForm()) return;

      isProcessing.value = true;
      paymentError.value = '';

      try {
        // Simuler un appel API
        await new Promise(resolve => setTimeout(resolve, 2000));

        // Simuler une validation de carte
        if (cardNumber.value.includes('4242')) {
          paymentSuccess.value = true;
          await store.dispatch('subscription/activateSubscription', selectedPlan.value);
          
          setTimeout(() => {
            router.push('/profile');
          }, 2000);
        } else {
          throw new Error('Carte refusée');
        }
      } catch (error) {
        paymentError.value = error.message || 'Erreur lors du paiement';
      } finally {
        isProcessing.value = false;
      }
    };

    return {
      cardNumber,
      expiry,
      cvc,
      cardName,
      isProcessing,
      paymentSuccess,
      paymentError,
      errors,
      selectedPlan,
      formatCardNumber,
      formatExpiry,
      processPayment
    };
  }
};
</script> 