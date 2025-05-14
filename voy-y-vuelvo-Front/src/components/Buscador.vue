<template>
  <div class="container">
    <div class="logo">
      <h1>Voy y Vuelvo</h1>
      <p>Más nuevo que nunca</p>
    </div>

    <div class="form-box">
      <div class="input-group">
        <label>Origen</label>
        <div class="input-icon">
          <span>📍</span>
          <input type="text" placeholder="Origen" />
        </div>
      </div>

      <div class="input-group">
        <label>Destino</label>
        <div class="input-icon">
          <span>📍</span>
          <input type="text" placeholder="Destino" />
        </div>
      </div>

      <div class="input-group">
        <label>Fecha</label>
        <div class="input-icon">
          <span>📅</span>
          <input type="date" />
        </div>
      </div>

      <button class="search-btn">Buscar</button>
    </div>

    <div class="bottom-icons">
      <button>👤</button>
      <button>❓</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BuscadorPrincipal',
}
</script>

<style scoped>
.container {
  background-color: #c4c4c4;
  height: 100vh;
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
</style>
