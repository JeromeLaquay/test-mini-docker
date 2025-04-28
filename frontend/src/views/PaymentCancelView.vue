<template>
  <div class="min-h-screen flex items-center justify-center bg-red-50">
    <div class="bg-white rounded-lg shadow-lg p-8 text-center">
      <h2 class="text-2xl font-bold text-red-700 mb-4">Paiement annulé</h2>
      <p class="text-gray-700 mb-6">Votre paiement a été annulé ou a échoué. Vous pouvez réessayer à tout moment.</p>
      <router-link to="/subscription" class="btn-secondary">Retour aux abonnements</router-link>
    </div>
  </div>
</template>

<script>
export default { name: 'PaymentCancelView' }
</script> 