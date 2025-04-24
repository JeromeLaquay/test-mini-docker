<template>
  <div class="music-upload min-h-screen bg-gradient-to-br from-purple-900 via-indigo-900 to-blue-900">
    <!-- Hero Section -->
    <header class="bg-black/30 backdrop-blur-sm shadow-lg">
      <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div class="text-center">
          <h1 class="text-4xl md:text-5xl font-bold text-white mb-6 leading-tight">
            Créez des Pochettes d'Albums Professionnelles
            <span class="block text-blue-400 mt-2">Pour vos Sets de DJ</span>
          </h1>
          <p class="text-xl text-gray-300 max-w-3xl mx-auto mb-8">
            Transformez vos playlists en véritables albums avec notre outil de création de pochettes intelligent.
            Idéal pour les DJs qui veulent se démarquer professionnellement.
          </p>
          <router-link 
            to="/albums" 
            class="inline-block bg-purple-600 hover:bg-purple-700 text-white font-bold py-4 px-8 rounded-full text-lg transition duration-200 transform hover:scale-105"
          >
            Commencer la Création
          </router-link>
        </div>
      </div>
    </header>

    <main>
      <!-- Caractéristiques du service -->
      <section class="py-16 px-4">
        <div class="max-w-7xl mx-auto">
          <h2 class="text-3xl font-bold text-white text-center mb-12">Pourquoi Choisir Notre Service ?</h2>
          
          <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="text-4xl mb-4">🎨</div>
              <h3 class="text-xl font-semibold text-white mb-3">Design Professionnel</h3>
              <p class="text-gray-300">Créez des pochettes d'albums qui reflètent votre identité de DJ avec notre format optimisé 12x12 cm.</p>
            </div>

            <div class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="text-4xl mb-4">⚡</div>
              <h3 class="text-xl font-semibold text-white mb-3">Création Instantanée</h3>
              <p class="text-gray-300">Générez votre pochette en quelques secondes. Importez simplement votre dossier de musique.</p>
            </div>

            <div class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="text-4xl mb-4">📱</div>
              <h3 class="text-xl font-semibold text-white mb-3">Compatible Tous Supports</h3>
              <p class="text-gray-300">Vos pochettes seront parfaites sur toutes les plateformes : réseaux sociaux, sites web, applications de streaming.</p>
            </div>
          </div>
        </div>
      </section>

      <!-- Témoignages -->
      <section class="py-16 px-4">
        <div class="max-w-7xl mx-auto">
          <h2 class="text-3xl font-bold text-white text-center mb-12">Ce que disent les DJs</h2>
          
          <!-- Formulaire d'ajout de témoignage -->
          <div class="max-w-2xl mx-auto mb-12 bg-white/5 backdrop-blur-sm p-6 rounded-xl border border-white/20">
            <h3 class="text-xl font-semibold text-white mb-6">Partagez votre expérience</h3>
            <form @submit.prevent="submitReview" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-300 mb-2">Votre nom d'artiste</label>
                <input
                  v-model="newReview.name"
                  type="text"
                  class="w-full bg-white/10 border border-white/20 rounded-lg px-4 py-2 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                  placeholder="DJ ..."
                  required
                />
              </div>
              
              <div>
                <label class="block text-sm font-medium text-gray-300 mb-2">Note</label>
                <div class="flex gap-2">
                  <button
                    v-for="star in 5"
                    :key="star"
                    type="button"
                    @click="newReview.rating = star"
                    class="text-2xl focus:outline-none"
                    :class="star <= newReview.rating ? 'text-yellow-400' : 'text-gray-500'"
                  >
                    ⭐
                  </button>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-300 mb-2">Votre message</label>
                <textarea
                  v-model="newReview.message"
                  rows="4"
                  class="w-full bg-white/10 border border-white/20 rounded-lg px-4 py-2 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                  placeholder="Partagez votre expérience..."
                  required
                ></textarea>
              </div>

              <button
                type="submit"
                class="w-full bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200"
                :disabled="isSubmitting"
              >
                {{ isSubmitting ? 'Envoi en cours...' : 'Publier mon avis' }}
              </button>
            </form>
          </div>

          <!-- Liste des témoignages -->
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <!-- Témoignages des utilisateurs -->
            <div v-for="review in userReviews" :key="review.id" class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="flex items-center mb-4">
                <div class="text-yellow-400 text-xl">
                  <span v-for="star in review.rating" :key="star">⭐</span>
                </div>
              </div>
              <p class="text-gray-300 mb-4">"{{ review.message }}"</p>
              <p class="text-white font-semibold">{{ review.name }}</p>
              <p class="text-sm text-gray-400">{{ formatDate(review.date) }}</p>
            </div>

            <!-- Témoignages fixes -->
            <div class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="flex items-center mb-4">
                <div class="text-yellow-400 text-xl">⭐⭐⭐⭐⭐</div>
              </div>
              <p class="text-gray-300 mb-4">"Un outil indispensable pour mes sets. La création de pochettes n'a jamais été aussi simple et professionnelle."</p>
              <p class="text-white font-semibold">DJ MaxPower</p>
            </div>

            <div class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="flex items-center mb-4">
                <div class="text-yellow-400 text-xl">⭐⭐⭐⭐⭐</div>
              </div>
              <p class="text-gray-300 mb-4">"Mes pochettes d'albums ont maintenant un look professionnel qui impressionne mes clients et followers."</p>
              <p class="text-white font-semibold">DJ ElectroBeats</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useToast } from 'vue-toastification';

export default {
  name: 'MusicUploadView',
  setup() {
    const toast = useToast();
    const userReviews = ref([]);
    const isSubmitting = ref(false);
    const newReview = ref({
      name: '',
      rating: 0,
      message: ''
    });

    const submitReview = async () => {
      if (newReview.value.rating === 0) {
        toast.error('Veuillez donner une note');
        return;
      }

      isSubmitting.value = true;

      try {
        const review = {
          id: Date.now(),
          ...newReview.value,
          date: new Date().toISOString()
        };

        // Récupérer les avis existants ou initialiser un tableau vide
        const existingReviews = JSON.parse(localStorage.getItem('userReviews') || '[]');
        
        // Ajouter le nouvel avis au début du tableau
        existingReviews.unshift(review);
        
        // Sauvegarder dans le localStorage
        localStorage.setItem('userReviews', JSON.stringify(existingReviews));
        
        // Mettre à jour l'état local
        userReviews.value = existingReviews;

        // Réinitialiser le formulaire
        newReview.value = {
          name: '',
          rating: 0,
          message: ''
        };

        toast.success('Merci pour votre témoignage !');
      } catch (error) {
        console.error('Erreur lors de l\'ajout du témoignage:', error);
        toast.error('Une erreur est survenue lors de l\'ajout de votre témoignage');
      } finally {
        isSubmitting.value = false;
      }
    };

    const formatDate = (date) => {
      return new Date(date).toLocaleDateString('fr-FR', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
      });
    };

    onMounted(() => {
      // Charger les avis depuis le localStorage
      const savedReviews = localStorage.getItem('userReviews');
      if (savedReviews) {
        userReviews.value = JSON.parse(savedReviews);
      }
    });

    return {
      userReviews,
      newReview,
      isSubmitting,
      submitReview,
      formatDate
    };
  },
  metaInfo: {
    title: 'Création de Pochettes d\'Albums pour DJs | JeroKa',
    meta: [
      { 
        name: 'description', 
        content: 'Créez des pochettes d\'albums professionnelles pour vos sets de DJ. Service rapide et automatisé de création de pochettes personnalisées pour DJs et producteurs de musique.' 
      },
      {
        name: 'keywords',
        content: 'création pochette album, DJ, cover art, design pochette, musique électronique, EDM, house music, techno, playlist artwork'
      },
      {
        property: 'og:title',
        content: 'Création de Pochettes d\'Albums pour DJs | JeroKa'
      },
      {
        property: 'og:description',
        content: 'Service professionnel de création de pochettes d\'albums pour DJs. Générez des visuels attractifs pour vos sets et playlists en quelques clics.'
      }
    ]
  }
};
</script>

<style scoped>
.music-upload {
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle at 75% 75%, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 40px 40px;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.4);
}
</style>