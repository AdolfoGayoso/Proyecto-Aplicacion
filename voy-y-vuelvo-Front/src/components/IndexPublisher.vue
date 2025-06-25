<template>
  <div class="publisher-container">
    <div class="header">
      <h1>Voy y Vuelvo</h1>
      <h2>Panel de Empresa</h2>
    </div>

    <!-- Información de la empresa -->
    <div class="company-info">
      <h3>{{ company.userName }}</h3>
      <div class="company-details">
        <p><strong>RUT:</strong> {{ company.rut || 'No registrado' }}</p>
        <p><strong>Email:</strong> {{ company.email }}</p>
      </div>
    </div>

    <!-- Viajes activos -->
    <div class="active-trips">
      <h3>Viajes Activos</h3>
      <table>
        <thead>
          <tr>
            <th>Origen - Destino</th>
            <th>Fecha</th>
            <th>Precio</th>
            <th>Asientos disponibles</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(trip, index) in activeTrips" :key="index">
            <td>
              {{ trip.origin }} - {{ trip.destination }}
              <button @click="verRecorrido(trip.stops)" class="action-btn material-icons" title="Ver recorrido">visibility</button>
            </td>
            <td>{{ formatDate(trip.departureDate) }}</td>
            <td>${{ trip.price }}</td>
            <td>{{ trip.availableSeats }}</td>
            <td>
              <button @click="deleteTrip(trip.id)" class="action-btn material-icons">delete</button>
              <button @click="verTickets(trip.id)" class="action-btn material-icons">confirmation_number</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Acciones -->
    <div class="actions">
      <button @click="mostrarFormulario = true" class="primary-btn">Crear nuevo viaje</button>
      <button @click="logout" class="secondary-btn">Cerrar sesión</button>
    </div>

    <!-- Modal Recorrido -->
    <div v-if="mostrarRecorrido" class="modal-overlay" @click.self="cerrarRecorrido">
      <div class="modal">
        <h3>Recorrido Completo</h3>
        <ul>
          <li v-for="(parada, i) in recorridoSeleccionado" :key="i">{{ parada.stop.name }}</li>
        </ul>
        <button @click="cerrarRecorrido" class="secondary-btn">Cerrar</button>
      </div>
    </div>

    <!-- Modal Crear Viaje -->
    <div v-if="mostrarFormulario" class="modal-overlay" @click.self="cerrarFormulario">
      <div class="modal">
        <h3>Crear nuevo viaje</h3>
        <form @submit.prevent="crearViaje">
          <input v-model="nuevoViaje.plateNumber" placeholder="Patente" required />
          <input v-model.number="nuevoViaje.price" placeholder="Precio" type="number" required />
          <input v-model="nuevoViaje.departureDate" placeholder="Fecha (YYYY-MM-DD)" required />
          <input v-model="nuevoViaje.departureTime" placeholder="Hora (HH:MM)" required />
          <input v-model.number="nuevoViaje.numTotalSeats" placeholder="Asientos" type="number" required />
          <input v-model="nuevoViaje.stopsIds" placeholder="IDs de paradas (ej: 1,2,3)" required />
          <button type="submit" class="primary-btn">Crear</button>
          <button type="button" @click="cerrarFormulario" class="secondary-btn">Cancelar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PublisherDashboard',
  data() {
    return {
      company: {
        userName: '',
        rut: '',
        email: '',
        publishedTrips: []
      },
      activeTrips: [],
      mostrarRecorrido: false,
      recorridoSeleccionado: [],
      mostrarFormulario: false,
      nuevoViaje: {
        plateNumber: '',
        price: null,
        departureDate: '',
        departureTime: '',
        numTotalSeats: null,
        stopsIds: ''
      }
    }
  },
  async created() {
    await this.obtenerDatosEmpresa();
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      date.setDate(date.getDate() + 1); // Ajuste por desfase de zona horaria
      const options = { year: 'numeric', month: 'long', day: 'numeric' }
      return date.toLocaleDateString('es-CL', options)
    },
    async obtenerDatosEmpresa() {
      const token = localStorage.getItem('token');
      try {
        const response = await fetch('http://localhost:8080/api/user/info', {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        const data = await response.json();
        this.company = data.data;
        if (this.company.publishedTrips) {
          this.activeTrips = this.company.publishedTrips.map(trip => {
            const availableSeats = trip.stops.reduce((acc, s) => acc + (s.availableSeats || 0), 0) / trip.stops.length;
            return {
              id: trip.id,
              origin: trip.stops[0]?.stop?.name || 'Origen',
              destination: trip.stops[trip.stops.length - 1]?.stop?.name || 'Destino',
              departureDate: trip.departureDate,
              price: trip.price,
              availableSeats: Math.floor(availableSeats),
              stops: trip.stops
            };
          });
        }
      } catch (error) {
        console.error('Error al obtener datos de empresa o viajes:', error);
      }
    },
    verRecorrido(stops) {
      this.recorridoSeleccionado = stops;
      this.mostrarRecorrido = true;
    },
    cerrarRecorrido() {
      this.mostrarRecorrido = false;
      this.recorridoSeleccionado = [];
    },
    cerrarFormulario() {
      this.mostrarFormulario = false;
      this.nuevoViaje = {
        plateNumber: '',
        price: null,
        departureDate: '',
        departureTime: '',
        numTotalSeats: null,
        stopsIds: ''
      };
    },
    async crearViaje() {
      const token = localStorage.getItem('token');
      const payload = {
        ...this.nuevoViaje,
        stopsIds: this.nuevoViaje.stopsIds.split(',').map(id => parseInt(id.trim()))
      };
      try {
        const response = await fetch('http://localhost:8080/api/trip/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        });
        const data = await response.json();
        if (response.ok) {
          this.cerrarFormulario();
          await this.obtenerDatosEmpresa();
        } else {
          alert(data.message);
        }
      } catch (error) {
        console.error('Error al crear viaje:', error);
      }
    },
    deleteTrip(id) {
      console.log('Eliminar viaje:', id)
    },
    verTickets(tripId) {
      this.$router.push({ path: '/tickets-publisher', query: { tripId } })
    },
    logout() {
      localStorage.removeItem('token')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');
@import url('https://fonts.googleapis.com/icon?family=Material+Icons');

.publisher-container {
  background-color: #adb8f2;
  min-height: 100vh;
  padding: 20px;
  font-family: 'Poppins', sans-serif;
}

.header {
  text-align: center;
  margin-bottom: 30px;
}

.company-info,
.active-trips {
  background-color: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.company-details {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
  margin-top: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ccc;
}

tr:hover {
  background-color: #f5f5f5;
}

.actions-cell {
  display: flex;
  gap: 10px;
}

.icon-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2em;
  color: #444;
  display: flex;
  align-items: center;
}

.actions {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 10px;
}

.primary-btn,
.secondary-btn {
  border: none;
  padding: 10px 22px;
  border-radius: 8px;
  font-size: 1em;
  font-weight: 500;
  cursor: pointer;
}

.primary-btn {
  background-color: #555;
  color: white;
}

.primary-btn:hover {
  background-color: #4ac2d4;
}

.secondary-btn {
  background-color: #f86c6c;
  color: white;
}

.secondary-btn:hover {
  background-color: #dd5555;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 12px;
  max-width: 400px;
  width: 90%;
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}

.modal form input {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

@media (max-width: 768px) {
  .company-details {
    grid-template-columns: 1fr;
  }

  .actions {
    flex-direction: column;
    align-items: stretch;
  }

  table {
    overflow-x: auto;
    display: block;
  }
}
</style>
