<template>
  <div class="container mx-auto py-12">
    <h2 class="text-2xl font-bold mb-6">Historique des paiements</h2>
    <table class="min-w-full bg-white rounded-lg shadow">
      <thead>
        <tr>
          <th class="px-6 py-3">Date</th>
          <th class="px-6 py-3">Montant</th>
          <th class="px-6 py-3">Statut</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="payment in payments" :key="payment.id">
          <td class="px-6 py-4">{{ payment.date }}</td>
          <td class="px-6 py-4">{{ payment.amount }} €</td>
          <td class="px-6 py-4">{{ payment.status }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'PaymentHistoryView',
  data() {
    return {
      payments: [
        { id: 1, date: '2024-06-01', amount: 9.99, status: 'Réussi' },
        { id: 2, date: '2024-05-01', amount: 9.99, status: 'Réussi' }
      ]
    }
  }
}
</script> 