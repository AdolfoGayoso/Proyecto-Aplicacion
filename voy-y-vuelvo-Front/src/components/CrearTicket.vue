<template>
  <div class="crear-ticket-container">
    <router-link to="/perfil" class="volver-link">← Volver al perfil</router-link>

    <h2>Crear Ticket para el viaje</h2>

    <div class="detalle-viaje" v-if="viaje">
      <p><strong>Origen - Destino:</strong> {{ viaje.origen }} - {{ viaje.destino }}</p>
      <p><strong>Fecha:</strong> {{ viaje.fecha }}</p>
      <p><strong>Empresa:</strong> {{ viaje.empresa }}</p>
    </div>

    <div class="formulario">
      <label for="descripcion">Motivo de la queja:</label>
      <textarea id="descripcion" v-model="descripcion" rows="5" placeholder="Escribe aquí tu queja..."></textarea>

      <button @click="enviarTicket">Enviar Ticket</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CrearTicket',
  data() {
    return {
      descripcion: '',
      viaje: null,
    };
  },
  mounted() {
    const id = this.$route.params.id;
    this.obtenerViaje(id);
  },
  methods: {
    async obtenerViaje(id) {
      try {
        const response = await fetch(`http://localhost:8080/api/trip/get-trip-by-id-${id}`);
        const result = await response.json();
        if (!response.ok) throw new Error(result.message || 'Error al obtener el viaje');

        const trip = result.data;

        this.viaje = {
          id: trip.id,
          origen: trip.stopOrders[0]?.stop.name || 'Origen desconocido',
          destino: trip.stopOrders.at(-1)?.stop.name || 'Destino desconocido',
          fecha: trip.departureDate,
          empresa: trip.publisherName || 'Empresa desconocida'
        };
      } catch (error) {
        alert('Error al cargar información del viaje: ' + error.message);
      }
    },

    async enviarTicket() {
      if (!this.descripcion.trim()) {
        alert('Por favor ingresa una descripción del problema.');
        return;
      }

      const token = localStorage.getItem('token');
      if (!token) {
        alert('Usuario no autenticado. Inicia sesión nuevamente.');
        this.$router.push('/login');
        return;
      }

      const ticketPayload = {
        tripId: this.viaje.id,
        description: this.descripcion
      };

      try {
        const response = await fetch('http://localhost:8080/api/ticket/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify(ticketPayload)
        });

        const result = await response.json();

        if (!response.ok) {
          throw new Error(result.message || 'No se pudo crear el ticket');
        }

        alert('Ticket enviado correctamente');
        this.$router.push('/perfil');
      } catch (error) {
        alert('Error al enviar el ticket: ' + error.message);
      }
    }
  }
};
</script>


<style scoped>
.crear-ticket-container {
  background-color: #e8eaf6;
  padding: 30px;
  font-family: 'Segoe UI', sans-serif;
  min-height: 100vh;
}

.volver-link {
  text-decoration: none;
  color: #3f51b5;
  font-weight: bold;
  margin-bottom: 20px;
  display: inline-block;
}

h2 {
  color: #3f51b5;
  margin-bottom: 20px;
}

.detalle-viaje {
  background-color: #c5cae9;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 20px;
}

.formulario {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
}

textarea {
  width: 100%;
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  resize: vertical;
  margin-bottom: 15px;
}

button {
  background-color: #7986cb;
  color: white;
  padding: 10px 20px;
  border-radius: 10px;
  border: none;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s;
}
button:hover {
  background-color: #5c6bc0;
}
</style>
