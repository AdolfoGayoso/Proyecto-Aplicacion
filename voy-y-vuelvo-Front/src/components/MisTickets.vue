<template>
  <div class="mis-tickets-container">
    <router-link to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Logo" class="corner-logo" />
    </router-link>

    <h2>Mis Tickets de Soporte</h2>

    <div v-if="tickets.length > 0" class="tickets-list">
      <div class="ticket" v-for="ticket in tickets" :key="ticket.id">
        <p><strong>Viaje:</strong> {{ ticket.trip?.origin }} → {{ ticket.trip?.destination }}</p>
        <p><strong>Título:</strong> {{ ticket.title }}</p>
        <p><strong>Descripción:</strong> {{ ticket.description }}</p>
        <p><strong>Estado:</strong> {{ ticket.status }}</p>
      </div>
    </div>

    <div v-else class="no-tickets">
      <p>No tienes tickets de soporte registrados.</p>
    </div>
  </div>
</template>

<script>
function tokenExpirado(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp < Math.floor(Date.now() / 1000);
  } catch {
    return true;
  }
}

export default {
  name: 'MisTickets',
  data() {
    return {
      tickets: []
    }
  },
  async created() {
    const token = localStorage.getItem('token');

    if (!token || tokenExpirado(token)) {
      alert('Sesión expirada. Inicia sesión nuevamente.');
      localStorage.removeItem('token');
      this.$router.push('/login');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/ticket/by-customer', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        console.warn('No se pudieron obtener los tickets');
        return;
      }

      const data = await response.json();
      this.tickets = data.data || [];
    } catch (error) {
      console.error('Error al obtener los tickets:', error);
    }
  }
}
</script>

<style scoped>
.mis-tickets-container {
  padding: 20px;
  font-family: 'Segoe UI', sans-serif;
  background-color: #e8eaf6;
  min-height: 100vh;
}
.corner-logo {
  width: 150px;
  margin-bottom: 20px;
}
.tickets-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.ticket {
  background-color: white;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
.no-tickets {
  padding: 20px;
  text-align: center;
  color: #666;
}
</style>
