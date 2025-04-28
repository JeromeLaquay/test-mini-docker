<template>
  <div class="min-h-screen flex items-center justify-center bg-green-50">
    <div class="bg-white rounded-lg shadow-lg p-8 text-center">
      <h2 class="text-2xl font-bold text-green-700 mb-4">Paiement réussi !</h2>
      <p class="text-gray-700 mb-6">Merci pour votre achat. Votre paiement a bien été pris en compte.</p>
      <router-link to="/profile" class="btn-primary">Retour au profil</router-link>
    </div>
  </div>
</template>

<script>
export default { name: 'PaymentSuccessView' }
</script> 