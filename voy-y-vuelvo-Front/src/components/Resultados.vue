<template>
  <div class="container">
    <router-link to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
    </router-link>

    <!-- Formulario para modificar búsqueda -->
    <div class="form-box">
      <!-- Origen -->
      <div class="input-group">
        <label>Origen</label>
        <div class="input-icon">
          <span class="material-icons">location_on</span>
          <input type="text" v-model="origin" placeholder="Origen" @input="filtrarSugerencias('origin')" @focus="filtrarSugerencias('origin')" />
        </div>
        <ul v-if="showOriginSuggestions && filteredStops.length && origin.length > 0" class="suggestions-list">
          <li v-for="stop in filteredStops" :key="stop.id" @click="seleccionarSugerencia(stop.name, 'origin')">
            {{ stop.name }}
          </li>
        </ul>
      </div>

      <!-- Destino -->
      <div class="input-group">
        <label>Destino</label>
        <div class="input-icon">
          <span class="material-icons">location_on</span>
          <input type="text" v-model="destination" placeholder="Destino" @input="filtrarSugerencias('destination')" @focus="filtrarSugerencias('destination')" />
        </div>
        <ul v-if="showDestinationSuggestions && filteredStops.length && destination.length > 0" class="suggestions-list">
          <li v-for="stop in filteredStops" :key="stop.id" @click="seleccionarSugerencia(stop.name, 'destination')">
            {{ stop.name }}
          </li>
        </ul>
      </div>

      <!-- Fecha -->
      <div class="input-group">
        <label>Fecha</label>
        <div class="input-icon">
          <span class="material-icons">calendar_today</span>
          <input type="date" v-model="date" />
        </div>
      </div>

      <!-- Hora -->
      <div class="input-group">
        <label>Hora</label>
        <div class="input-icon">
          <span class="material-icons">schedule</span>
          <input type="time" v-model="departureTime" />
        </div>
      </div>

      <button class="search-btn" @click="buscarViajes">Editar búsqueda</button>
    </div>

    <!-- Tabla de resultados -->
    <h2><span class="material-icons">search</span> Resultados de su búsqueda</h2>
    <p class="info-text">{{ results.length }} resultados encontrados</p>
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
          <td>
            <button class="comprar-btn" @click="redirigirCompra(trip)">Comprar</button>
          </td>
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
      allStops: [],
      filteredStops: [],
      showOriginSuggestions: false,
      showDestinationSuggestions: false
    }
  },
  mounted() {
    this.origin = this.$route.query.origin || ''
    this.destination = this.$route.query.destination || ''
    this.date = this.$route.query.date || ''
    this.departureTime = this.$route.query.departureTime || ''
    this.obtenerParadas()
    this.buscarViajes()
  },
  methods: {
    async obtenerParadas() {
      try {
        const response = await fetch('http://localhost:8080/api/stop/get-all')
        const result = await response.json()
        this.allStops = result.data || []
      } catch (error) {
        console.error('Error al obtener paradas:', error)
      }
    },
    filtrarSugerencias(campo) {
      const texto = (campo === 'origin' ? this.origin : this.destination).toLowerCase()
      this.filteredStops = this.allStops.filter(stop => stop.name.toLowerCase().includes(texto))
      if (campo === 'origin') this.showOriginSuggestions = true
      else this.showDestinationSuggestions = true
    },
    seleccionarSugerencia(nombre, campo) {
      if (campo === 'origin') {
        this.origin = nombre
        this.showOriginSuggestions = false
      } else {
        this.destination = nombre
        this.showDestinationSuggestions = false
      }
    },
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
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(requestBody)
        })

        if (!response.ok) {
          if (response.status === 404) {
            alert('No se encontraron viajes.')
            this.results = []
            return
          } else {
            const errorBody = await response.json()
            alert('Error al buscar viajes: ' + (errorBody.message || 'desconocido'))
            return
          }
        }

        const result = await response.json()
        this.results = Array.isArray(result.data) ? result.data : [result.data]

      } catch (error) {
        alert('Ocurrió un error al buscar viajes: ' + error.message)
      }
    },
    redirigirCompra(trip) {
      const stopIdFrom = trip.stops[0].stop.id
      const stopIdTo = trip.stops[trip.stops.length - 1].stop.id

      this.$router.push({
        path: '/compra',
        query: {
          tripId: trip.id,
          stopIdFrom,
          stopIdTo
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  background: linear-gradient(135deg, #9698d6, #b8baf3);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: 'Segoe UI', sans-serif;
  padding: 20px;
}
.logo-link {
  position: fixed;
  top: 15px;
  left: 15px;
  z-index: 1000;
}
.corner-logo {
  width: 150px;
  height: auto;
  cursor: pointer;
}
.form-box {
  display: flex;
  background-color: white;
  padding: 20px;
  border-radius: 20px;
  gap: 10px;
  align-items: end;
  flex-wrap: wrap;
  margin-bottom: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}
.input-group {
  display: flex;
  flex-direction: column;
  margin-right: 10px;
  position: relative;
}
.input-group label {
  font-size: 0.8em;
  margin-bottom: 5px;
  color: #1a1a1a;
}
.input-icon {
  display: flex;
  align-items: center;
  background-color: #e5dcfb;
  border-radius: 20px;
  padding: 5px 10px;
}
.input-icon span.material-icons {
  font-size: 20px;
  margin-right: 8px;
  color: black;
}
.input-icon input {
  border: none;
  background: transparent;
  outline: none;
  padding: 5px;
}
.search-btn {
  background-color: #9698d6;
  color: #111;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-weight: bold;
  cursor: pointer;
}
h2 {
  text-align: center;
  margin-bottom: 10px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
.info-text {
  text-align: center;
  color: white;
  font-style: italic;
  margin-bottom: 10px;
}
table {
  width: 100%;
  border-collapse: collapse;
  max-width: 1000px;
  margin-top: 10px;
  background-color: white;
  color: black;
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}
thead {
  background-color: #5c66b6;
  color: white;
}
thead th {
  padding: 10px;
  text-align: left;
}
tbody tr {
  background-color: #f0f1fb;
  transition: background-color 0.3s;
}
tbody tr:hover {
  background-color: #e2e4f9;
}
tbody td {
  padding: 10px;
}
.comprar-btn {
  background-color: #d3d6f5;
  border: none;
  border-radius: 10px;
  padding: 8px 15px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
}
.comprar-btn:hover {
  background-color: #a3a7e0;
}
.suggestions-list {
  list-style: none;
  margin: 0;
  padding: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  max-height: 150px;
  overflow-y: auto;
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 10;
  width: 100%;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}
.suggestions-list li {
  padding: 8px;
  cursor: pointer;
}
.suggestions-list li:hover {
  background-color: #f0f0f0;
}
</style>
