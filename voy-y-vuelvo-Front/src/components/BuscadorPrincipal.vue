<template>
  <div class="container">
    <div class="logo">
      <h1>Voy y Vuelvo</h1>
      <p>Más nuevo que nunca</p>
    </div>

    <div class="form-box">
      <!-- Origen -->
      <div class="input-group">
  <label>Origen</label>
  <div class="input-icon">
    <span>📍</span>
    <input
      type="text"
      v-model="origin"
      placeholder="Origen"
      @input="filtrarSugerencias('origin')"
      @focus="filtrarSugerencias('origin')"
    />
  </div>
  <!-- Lista de sugerencias -->
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
    <span>📍</span>
    <input
      type="text"
      v-model="destination"
      placeholder="Destino"
      @input="filtrarSugerencias('destination')"
      @focus="filtrarSugerencias('destination')"
    />
  </div>
  <!-- Lista de sugerencias para DESTINO -->
  <ul
    v-if="showDestinationSuggestions && filteredStops.length && destination.length > 0"
    class="suggestions-list"
  >
    <li
      v-for="stop in filteredStops"
      :key="stop.id"
      @click="seleccionarSugerencia(stop.name, 'destination')"
    >
      {{ stop.name }}
    </li>
  </ul>
</div>


      <!-- Fecha -->
      <div class="input-group">
        <label>Fecha</label>
        <div class="input-icon">
          <span>📅</span>
          <input type="date" v-model="date" />
        </div>
      </div>

      <!-- Hora -->
      <div class="input-group">
        <label>Hora de salida</label>
        <div class="input-icon">
          <span>⏰</span>
          <input type="time" v-model="departureTime" />
        </div>
      </div>

      <!-- Botón Buscar -->
      <button class="search-btn" @click="buscar">Buscar</button>
    </div>

    <!-- Botones abajo -->
    <div class="bottom-icons">
      <button @click="$router.push('/login')">👤</button>
      <button>❓</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BuscadorPrincipal',
  data() {
    return {
      origin: '',
      destination: '',
      date: '',
      departureTime: '',
      allStops: [],
      filteredStops: [],
      showOriginSuggestions: false,
      showDestinationSuggestions: false,
    }
  },
  mounted() {
    this.obtenerParadas()
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
      this.filteredStops = this.allStops.filter(stop =>
        stop.name.toLowerCase().includes(texto)
      )
      if (campo === 'origin') {
        this.showOriginSuggestions = true
      } else {
        this.showDestinationSuggestions = true
      }
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
    hideSuggestions(campo) {
      if (campo === 'origin') {
        this.showOriginSuggestions = false
      } else {
        this.showDestinationSuggestions = false
      }
    },
    async buscar() {
      try {
        const requestBody = {
          origin: this.origin,
          destination: this.destination,
          date: this.date,
          departureTime: this.departureTime ? this.departureTime : null
        }

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
            return
          } else {
            const errorBody = await response.json()
            console.error('Error del servidor:', errorBody)
            alert('Error al buscar viajes: ' + (errorBody.message || 'desconocido'))
            return
          }
        }

        // Si todo OK → redirigir a resultados
        this.$router.push({
          path: '/resultados',
          query: {
            origin: this.origin,
            destination: this.destination,
            date: this.date,
            departureTime: this.departureTime || '',
          }
        })

      } catch (error) {
        console.error('Error en la petición:', error)
        alert('Ocurrió un error al buscar viajes: ' + error.message)
      }
    }
  }
}
</script>


<style scoped>
.container {
  background-color: #c4c4c4;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: 'Segoe UI', sans-serif;
}
.logo {
  text-align: center;
  margin-bottom: 30px;
}
.logo img {
  height: 80px;
}
.logo h1 {
  margin: 10px 0 0;
  color: #2c3e50;
}
.logo p {
  margin: 5px 0;
  color: #555;
  font-size: 1.1em;
}
.form-box {
  display: flex;
  background-color: #9698d6;
  padding: 20px;
  border-radius: 20px;
  gap: 10px;
  align-items: end;
  flex-wrap: wrap;
}
.input-group {
  display: flex;
  flex-direction: column;
  margin-right: 10px;
  position: relative; /* <--- ESTE es el cambio clave */
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
.input-icon span {
  margin-right: 8px;
}
.input-icon input {
  border: none;
  background: transparent;
  outline: none;
  padding: 5px;
}
.search-btn {
  background-color: #5dd0e7;
  color: #111;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-weight: bold;
  cursor: pointer;
}
.bottom-icons {
  margin-top: 20px;
  display: flex;
  gap: 20px;
}
.bottom-icons button {
  background-color: #9698d6;
  border: none;
  border-radius: 20px;
  padding: 10px 15px;
  font-size: 1.2em;
  cursor: pointer;
}
.results {
  margin-top: 20px;
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  width: 80%;
  max-width: 600px;
}
.results h2 {
  margin-bottom: 10px;
}
.results ul {
  list-style: none;
  padding: 0;
}
.results li {
  margin-bottom: 5px;
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
  top: 100%; /* <--- NUEVO */
  left: 0;   /* <--- NUEVO */
  z-index: 10;
  width: 100%;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1); /* opcional, para que se vea mejor */
}
.suggestions-list li {
  padding: 8px;
  cursor: pointer;
}
.suggestions-list li:hover {
  background-color: #f0f0f0;
}
</style>

