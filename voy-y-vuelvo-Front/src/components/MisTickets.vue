<template>
  <div class="mis-tickets-container">
    <h1>Mis Tickets de Soporte</h1>

    <div v-if="tickets.length > 0" class="tabla">
      <div class="fila titulo">
        <span>ID</span>
        <span>Título</span>
        <span>Descripción</span>
        <span>Estado</span>
        <span>Fecha</span>
      </div>

      <div class="fila" v-for="ticket in tickets" :key="ticket.id">
        <span>{{ ticket.id }}</span>
        <span>{{ ticket.title }}</span>
        <span>{{ ticket.description }}</span>
        <span>{{ traducirEstado(ticket.status) }}</span>
        <span>{{ formatDate(ticket.createdAt) }}</span>
      </div>
    </div>

    <div v-else class="no-tickets">
      <p>Aún no has creado tickets de soporte.</p>
    </div>
  </div>
</template>

<script>
function tokenExpirado(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const ahora = Math.floor(Date.now() / 1000);
    return payload.exp < ahora;
  } catch {
    return true;
  }
}

export default {
  name: 'MisTickets',
  data() {
    return {
      tickets: []
    };
  },
  created() {
    this.cargarTickets();
  },
  methods: {
    async cargarTickets() {
      const token = localStorage.getItem('token');
      if (!token || tokenExpirado(token)) {
        alert('Sesión expirada. Por favor, inicie sesión nuevamente.');
        localStorage.removeItem('token');
        this.$router.push('/login');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/api/ticket/customer', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        const result = await response.json();

        if (response.ok) {
          this.tickets = result.data || [];
        } else {
          console.warn('No se pudieron cargar los tickets:', result.message);
        }
      } catch (error) {
        console.error('Error al cargar los tickets:', error);
      }
    },
    traducirEstado(estado) {
      switch (estado) {
        case 'OPEN': return 'Abierto';
        case 'IN_PROGRESS': return 'En Proceso';
        case 'RESOLVED': return 'Resuelto';
        default: return estado;
      }
    },
    formatDate(fechaISO) {
      const fecha = new Date(fechaISO);
      return fecha.toLocaleString();
    }
  }
}
</script>

<style scoped>
.mis-tickets-container {
  padding: 30px;
  font-family: 'Segoe UI', sans-serif;
  background-color: #7986cb;
  min-height: 100vh;
}

.tabla {
  margin-top: 20px;
}

.fila {
  display: grid;
  grid-template-columns: 0.5fr 1fr 2fr 1fr 1fr;
  align-items: center;
  background-color: white;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 12px;
  text-align: center;
}

.fila.titulo {
  font-weight: bold;
  background-color: #c5cae9;
}

.no-tickets {
  background-color: white;
  border: 1px solid #7986cb;
  padding: 15px;
  border-radius: 10px;
  margin-top: 20px;
  text-align: center;
}
</style>
