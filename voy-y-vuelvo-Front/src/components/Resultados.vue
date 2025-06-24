<template>
  <div class="resultados-container">
    <!-- Formulario para modificar búsqueda -->
    <div class="form-box">
      <!-- Origen -->
      <div class="input-group">
        <label>Origen</label>
        <input type="text" v-model="origin" placeholder="Origen" />
      </div>

      <!-- Destino -->
      <div class="input-group">
        <label>Destino</label>
        <input type="text" v-model="destination" placeholder="Destino" />
      </div>

      <!-- Fecha -->
      <div class="input-group">
        <label>Fecha</label>
        <input type="date" v-model="date" />
      </div>

      <!-- Hora -->
      <div class="input-group">
        <label>Hora (opcional)</label>
        <input type="time" v-model="departureTime" />
      </div>

      <!-- Botón Editar -->
      <button @click="buscarViajes">Editar búsqueda</button>
    </div>

    <!-- Tabla de resultados -->
    <h2>Resultados de su búsqueda</h2>
    <table>
      <thead>
        <tr>
          <th>Origen - Destino</th>
          <th>Hora</th>
          <th>Empresa</th>
          <th>Precio</th>
          <th>Acción</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(trip, index) in results" :key="index">
          <td>{{ trip.stops[0].stop.name }} → {{ trip.stops[trip.stops.length - 1].stop.name }}</td>
          <td>{{ trip.departureTime }}</td>
          <td>Empresa X</td>
          <td>{{ trip.price || '$10000' }}</td>
          <td><button class="comprar-btn">Comprar</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'Resultado_busqueda',
  data() {
    return {
      origin: '',
      destination: '',
      date: '',
      departureTime: '',
      results: [],
    }
  },
  mounted() {
    // Al cargar la página, inicializar con los datos de la URL
    this.origin = this.$route.query.origin || ''
    this.destination = this.$route.query.destination || ''
    this.date = this.$route.query.date || ''
    this.departureTime = this.$route.query.departureTime || ''
    this.buscarViajes()
  },
  methods: {
    async buscarViajes() {
      const requestBody = {
        origin: this.origin,
        destination: this.destination,
        date: this.date,
        departureTime: this.departureTime || null
      }

      try {
        const response = await fetch('http://localhost:8080/api/trip/filter', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(requestBody)
        })

        if (!response.ok) {
          if (response.status === 404) {
            alert('No se encontraron viajes.')
            this.results = []
            return
          } else {
            const errorBody = await response.json()
            console.error('Error del servidor:', errorBody)
            alert('Error al buscar viajes: ' + (errorBody.message || 'desconocido'))
            return
          }
        }

        const result = await response.json()
        this.results = Array.isArray(result.data) ? result.data : [result.data]

      } catch (error) {
        console.error('Error en la petición:', error)
        alert('Ocurrió un error al buscar viajes: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.resultados-container {
  background-color: #3f468c;
  padding: 20px;
  border-radius: 15px;
  max-width: 1000px;
  margin: 20px auto;
  color: white;
  font-family: 'Segoe UI', sans-serif;
}
.form-box {
  display: flex;
  background-color: #9698d6;
  padding: 15px;
  border-radius: 20px;
  gap: 10px;
  align-items: end;
  flex-wrap: wrap;
  margin-bottom: 20px;
}
.input-group {
  display: flex;
  flex-direction: column;
  margin-right: 10px;
}
.input-group label {
  font-size: 0.8em;
  margin-bottom: 5px;
  color: #fff;
}
.input-group input {
  border: none;
  border-radius: 10px;
  padding: 8px;
  font-size: 1em;
}
button {
  background-color: #5dd0e7;
  color: #111;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-weight: bold;
  cursor: pointer;
}
h2 {
  text-align: center;
  margin-bottom: 15px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
thead {
  background-color: #5c66b6;
}
thead th {
  padding: 10px;
  text-align: left;
}
tbody tr {
  background-color: #7e84c9;
  margin-bottom: 5px;
}
tbody td {
  padding: 10px;
}
.comprar-btn {
  background-color: #c7caf1;
  border: none;
  border-radius: 10px;
  padding: 8px 15px;
  cursor: pointer;
  font-weight: bold;
}
.comprar-btn:hover {
  background-color: #e0e2fa;
}
</style>
