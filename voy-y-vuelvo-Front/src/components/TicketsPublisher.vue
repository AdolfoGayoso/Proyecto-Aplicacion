<template>
  <div class="tickets-publisher-container">
    <router-link to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
    </router-link>

    <h2>Tickets de Soporte</h2>

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
        <span>{{ ticket.status }}</span>
        <span>{{ formatDate(ticket.createdAt) }}</span>
      </div>
    </div>

    <div v-else>
      <p style="text-align: center; padding: 20px;">No hay tickets para este viaje.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TicketsPublisher',
  data() {
    return {
      tickets: []
    }
  },
  mounted() {
    this.obtenerTickets()
  },
  methods: {
    async obtenerTickets() {
      const token = localStorage.getItem('token')
      const tripIdFiltrar = this.$route.query.tripId

      try {
        const res = await fetch('http://localhost:8080/api/ticket/publisher', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })

        const result = await res.json()
        if (!res.ok) throw new Error(result.message || 'Error al obtener tickets')

        if (tripIdFiltrar) {
          this.tickets = result.data.filter(t => t.trip?.id == tripIdFiltrar)
        } else {
          this.tickets = result.data
        }
      } catch (e) {
        alert('Error al cargar tickets: ' + e.message)
      }
    },
    formatDate(dateStr) {
      const fecha = new Date(dateStr)
      return fecha.toLocaleDateString('es-CL') + ' ' + fecha.toLocaleTimeString('es-CL')
    }
  }
}
</script>

<style scoped>
.tickets-publisher-container {
  padding: 2rem;
}

.tabla {
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
}

.fila {
  display: grid;
  grid-template-columns: 50px 150px 1fr 120px 160px;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.fila.titulo {
  background-color: #f0f0f0;
  font-weight: bold;
}
</style>
