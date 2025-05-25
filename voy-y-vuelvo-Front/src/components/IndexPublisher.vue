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
              <button @click="editTrip(trip.id)" class="action-btn">✏️</button>
              <button @click="deleteTrip(trip.id)" class="action-btn">🗑️</button>
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
      // Lógica para crear nuevo viaje
      this.$router.push('/crear-viaje')
    },
    editTrip(id) {
      // Lógica para editar viaje
      console.log('Editar viaje:', id)
    },
    deleteTrip(id) {
      // Lógica para eliminar viaje
      console.log('Eliminar viaje:', id)
    },
    logout() {
      // Lógica para cerrar sesión
      localStorage.removeItem('token')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

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

.header h1 {
  color: #3a3a3a;
  margin-bottom: 5px;
  font-weight: 600;
}

.header h2 {
  color: #5f5f5f;
  font-size: 1.1em;
}

.company-info {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.company-info h3 {
  margin-top: 0;
  color: #354259;
}

.company-details {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
  margin-top: 10px;
}

.active-trips {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.active-trips h3 {
  margin-top: 0;
  color: #354259;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #0d0c0c;
}

th {
  background-color: #f2f6fb;
  font-weight: 600;
  color: #2f3b52;
}

tr:hover {
  background-color: #8e9299;
}

.action-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2em;
  margin: 0 5px;
  color: #4a4a4a;
}

.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.primary-btn {
  background-color: #555;
  color: white;
  border: none;
  padding: 10px 22px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1em;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.primary-btn:hover {
  background-color: #4ac2d4;
}

.secondary-btn {
  background-color: #f86c6c;
  color: white;
  border: none;
  padding: 10px 22px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1em;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.secondary-btn:hover {
  background-color: #dd5555;
}

@media (max-width: 768px) {
  .company-details {
    grid-template-columns: 1fr;
  }

  table {
    display: block;
    overflow-x: auto;
  }

  .actions {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>