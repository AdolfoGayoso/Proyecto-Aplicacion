<template>
  <div class="publisher-container">
    <div class="header">
      <h1>Voy y Vuelvo</h1>
      <h2>Panel de Empresa</h2>
    </div>

    <!-- Información de la empresa -->
    <div class="company-info">
      <h3>{{ company.name }}</h3>
      <div class="company-details">
        <p><strong>RUT:</strong> {{ company.rut }}</p>
        <p><strong>Email:</strong> {{ company.email }}</p>
        <p><strong>Teléfono:</strong> {{ company.phone }}</p>
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
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(trip, index) in activeTrips" :key="index">
            <td>{{ trip.origin }} - {{ trip.destination }}</td>
            <td>{{ formatDate(trip.date) }}</td>
            <td>${{ trip.price }}</td>
            <td>
              <button @click="editTrip(trip.id)" class="action-btn material-icons">edit</button>
              <button @click="deleteTrip(trip.id)" class="action-btn material-icons">delete</button>
              <button @click="verTickets(trip.id)" class="action-btn material-icons">confirmation_number</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Acciones -->
    <div class="actions">
      <button @click="createNewTrip" class="primary-btn">Crear nuevo viaje</button>
      <button @click="logout" class="secondary-btn">Cerrar sesión</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PublisherDashboard',
  data() {
    return {
      company: {
        name: 'Nombre Empresa',
        rut: '12.345.678-9',
        email: 'correo@gmail.com',
        phone: '+569 12345678'
      },
      activeTrips: [
        {
          id: 1,
          origin: 'Santiago',
          destination: 'Valparaíso',
          date: '2023-12-15',
          price: 5000
        },
        {
          id: 2,
          origin: 'Valparaíso',
          destination: 'Viña del Mar',
          date: '2023-12-16',
          price: 2000
        }
      ]
    }
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('es-CL', options)
    },
    createNewTrip() {
      this.$router.push('/crear-viaje')
    },
    editTrip(id) {
      console.log('Editar viaje:', id)
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
