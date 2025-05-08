<template>
  <div class="plan-selected min-h-screen bg-gradient-to-br from-purple-900 via-indigo-900 to-blue-900 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-3xl mx-auto">
      <!-- En-tête -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-extrabold text-white sm:text-5xl">
          <span class="block">Finaliser votre abonnement</span>
        </h1>
        <p class="mt-3 text-white/70">
          Vous avez choisi le plan {{ selectedPlan?.nom }}
        </p>
      </div>

      <!-- Résumé du plan -->
      <div class="bg-white/10 backdrop-blur-md rounded-xl p-6 mb-8">
        <h2 class="text-2xl font-bold text-white mb-4">Résumé de votre plan</h2>
        <div class="space-y-3 text-white/90">
          <p><span class="font-medium">Plan :</span> {{ selectedPlan?.nom }}</p>
          <p><span class="font-medium">Prix mensuel :</span> {{ formatPrice(selectedPlan?.prixMensuel) }}€</p>
          <p v-if="selectedPlan?.prixAnnuel"><span class="font-medium">Prix annuel :</span> {{ formatPrice(selectedPlan?.prixAnnuel) }}€</p>
          <p v-if="selectedPlan?.periodeEssaiJours" class="text-purple-400">
            {{ selectedPlan.periodeEssaiJours }} jours d'essai gratuit inclus
          </p>
        </div>
      </div>

      <!-- Formulaire de paiement -->
      <div class="bg-white/10 backdrop-blur-md rounded-xl p-6">
        <h2 class="text-2xl font-bold text-white mb-6">Informations de paiement</h2>
        
        <form @submit.prevent="handlePayment" class="space-y-6">
          <!-- Informations du payeur -->
          <div class="space-y-4">
            <h3 class="text-xl font-semibold text-white">Informations personnelles</h3>
            
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="firstName" class="block text-white font-medium mb-2">Prénom</label>
                <input
                  id="firstName"
                  v-model="payerInfo.firstName"
                  type="text"
                  required
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="John"
                />
              </div>
              <div>
                <label for="lastName" class="block text-white font-medium mb-2">Nom</label>
                <input
                  id="lastName"
                  v-model="payerInfo.lastName"
                  type="text"
                  required
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="Doe"
                />
              </div>
            </div>

            <div>
              <label for="email" class="block text-white font-medium mb-2">Email</label>
              <input
                id="email"
                v-model="payerInfo.email"
                type="email"
                required
                class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                placeholder="john.doe@example.com"
              />
            </div>

            <div>
              <label for="phone" class="block text-white font-medium mb-2">Téléphone</label>
              <input
                id="phone"
                v-model="payerInfo.phone"
                type="tel"
                class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                placeholder="+33 6 12 34 56 78"
              />
            </div>

            <div>
              <label for="address" class="block text-white font-medium mb-2">Adresse de facturation</label>
              <input
                id="address"
                v-model="payerInfo.address"
                type="text"
                required
                class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                placeholder="123 rue Example"
              />
            </div>

            <div class="grid grid-cols-3 gap-4">
              <div>
                <label for="postalCode" class="block text-white font-medium mb-2">Code postal</label>
                <input
                  id="postalCode"
                  v-model="payerInfo.postalCode"
                  type="text"
                  required
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="75000"
                />
              </div>
              <div class="col-span-2">
                <label for="city" class="block text-white font-medium mb-2">Ville</label>
                <input
                  id="city"
                  v-model="payerInfo.city"
                  type="text"
                  required
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="Paris"
                />
              </div>
            </div>
          </div>

          <!-- Méthode de paiement -->
          <div class="space-y-4">
            <h3 class="text-xl font-semibold text-white">Méthode de paiement</h3>
            
            <!-- Méthodes de paiement sauvegardées -->
            <div v-if="safeMethodesPaiement && safeMethodesPaiement.length > 0" class="grid grid-cols-1 gap-4 mb-4">
              <button
                v-for="method in safeMethodesPaiement"
                :key="method.id"
                type="button"
                @click="selectSavedPaymentMethod(method)"
                :class="[
                  'p-4 rounded-lg border text-left transition-colors flex justify-between items-center',
                  selectedPaymentMethod?.id === method.id
                    ? 'bg-purple-600 border-purple-400 text-white'
                    : 'border-white/20 text-white/70 hover:border-purple-400'
                ]"
              >
                <div>
                  <span class="block font-medium">{{ method.type === 'CARTE' ? '💳 Carte terminant par ' + method.numeroCarte.slice(-4) : '🌐 Compte PayPal' }}</span>
                  <span class="text-sm opacity-75">{{ method.nomTitulaire }}</span>
                </div>
                <button 
                  @click.stop="deletePaymentMethod(method.id)"
                  class="text-white/50 hover:text-red-400"
                >
                  🗑️
                </button>
              </button>
            </div>

            <!-- Nouvelle méthode de paiement -->
            <div class="grid grid-cols-2 gap-4">
              <button
                v-for="method in availablePaymentMethods"
                :key="method.id"
                type="button"
                @click="selectNewPaymentMethod(method)"
                :class="[
                  'p-4 rounded-lg border text-center transition-colors',
                  isNewMethodSelected(method)
                    ? 'bg-purple-600 border-purple-400 text-white'
                    : 'border-white/20 text-white/70 hover:border-purple-400'
                ]"
              >
                <span class="block text-xl mb-1">{{ method.icon }}</span>
                Nouvelle {{ method.name }}
              </button>
            </div>
          </div>

          <!-- Formulaire carte bancaire -->
          <div v-if="selectedPaymentMethod?.type === 'CARTE'" class="space-y-4">
            <div>
              <label for="cardNumber" class="block text-white font-medium mb-2">Numéro de carte</label>
              <div class="relative">
                <input
                  id="cardNumber"
                  v-model="cardInfo.number"
                  type="text"
                  required
                  maxlength="19"
                  @input="formatCardNumber"
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="4242 4242 4242 4242"
                />
                <span class="absolute right-3 top-1/2 transform -translate-y-1/2 text-white/50">
                  {{ getCardType() }}
                </span>
              </div>
            </div>

            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="expiry" class="block text-white font-medium mb-2">Date d'expiration</label>
                <input
                  id="expiry"
                  v-model="cardInfo.expiry"
                  type="text"
                  required
                  maxlength="5"
                  @input="formatExpiry"
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="MM/YY"
                />
              </div>
              <div>
                <label for="cvc" class="block text-white font-medium mb-2">
                  CVC
                  <span class="text-white/50 text-sm ml-1">(3 chiffres)</span>
                </label>
                <input
                  id="cvc"
                  v-model="cardInfo.cvc"
                  type="text"
                  required
                  maxlength="3"
                  class="w-full px-3 py-2 bg-black/40 border border-white/20 rounded-lg text-white placeholder-white/50 focus:outline-none focus:border-purple-500"
                  placeholder="123"
                />
              </div>
            </div>
          </div>

          <!-- Résumé de la commande -->
          <div class="border-t border-white/10 pt-6 mt-6">
            <h3 class="text-xl font-semibold text-white mb-4">Résumé de la commande</h3>
            <div class="space-y-2 text-white/90">
              <div class="flex justify-between">
                <span>{{ selectedPlan?.nom }}</span>
                <span>{{ formatPrice(selectedPlan?.prixMensuel) }}€</span>
              </div>
              <div v-if="selectedPlan?.periodeEssaiJours" class="flex justify-between text-purple-400">
                <span>Période d'essai</span>
                <span>{{ selectedPlan.periodeEssaiJours }} jours gratuits</span>
              </div>
              <div class="flex justify-between font-bold pt-2 border-t border-white/10">
                <span>Total à payer aujourd'hui</span>
                <span>{{ formatPrice(selectedPlan?.prixMensuel) }}€</span>
              </div>
            </div>
          </div>

          <!-- Conditions générales -->
          <div class="flex items-start space-x-2">
            <input
              type="checkbox"
              id="terms"
              v-model="acceptedTerms"
              class="mt-1"
              required
            />
            <label for="terms" class="text-white/70 text-sm">
              J'accepte les <a href="#" class="text-purple-400 hover:text-purple-300">conditions générales</a> et la <a href="#" class="text-purple-400 hover:text-purple-300">politique de confidentialité</a>
            </label>
          </div>

          <!-- Message d'erreur -->
          <div v-if="error" class="p-4 rounded-lg bg-red-500/20 border border-red-500/30 text-red-200">
            {{ error }}
          </div>

          <!-- Bouton de soumission -->
          <button
            type="submit"
            class="w-full bg-purple-600 text-white rounded-lg px-4 py-3 font-medium hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition-colors duration-200 disabled:opacity-50"
            :disabled="!canSubmit"
          >
            <span v-if="loading">
              <span class="animate-spin inline-block mr-2">⏳</span>
              Traitement en cours...
            </span>
            <span v-else>
              Payer {{ formatPrice(selectedPlan?.prixMensuel) }}€
            </span>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'PlanSelected',
  
  data() {
    return {
      selectedPlan: null,
      selectedPaymentMethod: null,
      isNewPaymentMethod: false,
      payerInfo: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        address: '',
        postalCode: '',
        city: ''
      },
      cardInfo: {
        number: '',
        expiry: '',
        cvc: ''
      },
      acceptedTerms: false,
      error: null,
      loading: false
    };
  },

  computed: {
    ...mapGetters('auth', ['isLoggedIn', 'currentUser']),
    ...mapGetters('payment', ['processingPayment']),
    ...mapGetters('methodePaiement', [
      'methodesPaiement',
      'isLoading',
      'error'
    ]),

    // S'assurer que methodesPaiement a une valeur par défaut
    safeMethodesPaiement() {
      return this.methodesPaiement || [];
    },
    
    availablePaymentMethods() {
      return [
        { id: 'card', type: 'CARTE', name: 'Carte bancaire', icon: '💳' },
        { id: 'paypal', type: 'PAYPAL', name: 'PayPal', icon: '🌐' }
      ];
    },

    canSubmit() {
      return !this.loading && 
             !this.isLoading && 
             this.selectedPaymentMethod && 
             this.acceptedTerms &&
             this.isValidForm();
    }
  },

  created() {
    this.initializeView();
  },

  methods: {
    async initializeView() {
      try {
        const savedPlan = localStorage.getItem('selectedPlan');
        if (!savedPlan) {
          this.$router.push('/plans');
          return;
        }

        if (!this.isLoggedIn) {
          localStorage.setItem('redirectAfterLogin', this.$route.fullPath);
          this.$router.push('/login');
          return;
        }

        this.selectedPlan = JSON.parse(savedPlan);
        
        if (this.currentUser) {
          this.payerInfo.email = this.currentUser.email || '';
          this.payerInfo.firstName = this.currentUser.firstName || '';
          this.payerInfo.lastName = this.currentUser.lastName || '';
          
          // Charger les méthodes de paiement de l'utilisateur
          await this.$store.dispatch('methodePaiement/fetchMethodesPaiement', this.currentUser.id);
        }
      } catch (error) {
        console.error('Error in initializeView:', error);
        this.error = "Erreur lors de l'initialisation de la page";
      }
    },

    formatPrice(price) {
      return price ? Number(price).toFixed(2) : '0.00';
    },

    selectSavedPaymentMethod(method) {
      if (!method) return;
      this.selectedPaymentMethod = method;
      this.isNewPaymentMethod = false;
      this.error = null;
    },

    selectNewPaymentMethod(method) {
      if (!method) return;
      this.selectedPaymentMethod = method;
      this.isNewPaymentMethod = true;
      this.error = null;
    },

    isNewMethodSelected(method) {
      return this.isNewPaymentMethod && this.selectedPaymentMethod?.id === method?.id;
    },

    async deletePaymentMethod(id) {
      if (!id) return;
      
      try {
        this.loading = true;
        await this.$store.dispatch('methodePaiement/deleteMethodePaiement', id);
        if (this.currentUser) {
          await this.$store.dispatch('methodePaiement/fetchMethodesPaiement', this.currentUser.id);
        }
      } catch (error) {
        console.error('Error deleting payment method:', error);
        this.error = "Erreur lors de la suppression de la méthode de paiement";
      } finally {
        this.loading = false;
      }
    },

    formatCardNumber(event) {
      let value = event.target.value.replace(/\D/g, '');
      value = value.replace(/(\d{4})/g, '$1 ').trim();
      this.cardInfo.number = value;
    },

    formatExpiry(event) {
      let value = event.target.value.replace(/\D/g, '');
      if (value.length >= 2) {
        value = value.slice(0, 2) + '/' + value.slice(2);
      }
      this.cardInfo.expiry = value;
    },

    getCardType() {
      const number = this.cardInfo.number.replace(/\D/g, '');
      if (number.startsWith('4')) return '💳 Visa';
      if (number.startsWith('5')) return '💳 MasterCard';
      if (number.startsWith('3')) return '💳 Amex';
      return '💳';
    },

    isValidForm() {
      if (this.selectedPaymentMethod?.type === 'carte') {
        return this.cardInfo.number.length >= 16 &&
               this.cardInfo.expiry.length === 5 &&
               this.cardInfo.cvc.length === 3;
      }
      return true;
    },

    async handlePayment() {
      if (!this.canSubmit) return;

      this.error = null;
      this.loading = true;
      
      try {
        // Préparation des données de paiement
        const paymentData = {
          planId: this.selectedPlan.id,
          amount: this.selectedPlan.prixMensuel,
          paymentMethod: this.selectedPaymentMethod.type,
          firstName: this.payerInfo.firstName,
          lastName: this.payerInfo.lastName,
          email: this.payerInfo.email,
          phone: this.payerInfo.phone,
          address: this.payerInfo.address,
          postalCode: this.payerInfo.postalCode,
          city: this.payerInfo.city,
          number: this.cardInfo.number,
          expiry: this.cardInfo.expiry,
          cvc: this.cardInfo.cvc
        };

        //payer par carte
        await this.$store.dispatch('payment/payByCard', paymentData);

      } catch (error) {
        this.error = error.response?.data?.message || 'Une erreur est survenue lors du paiement';
        console.error('Erreur de paiement:', error);
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.plan-selected {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style>
