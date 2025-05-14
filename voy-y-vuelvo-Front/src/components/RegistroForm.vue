<template>
  <div class="registro-container">
    <div class="registro-card">
      <h2>Registro</h2>

      <label for="email">Correo electronico</label>
      <input type="email" id="email" />

      <label for="password">Contraseña</label>
      <input type="password" id="password" />

      <label for="confirmPassword">Confirmar contraseña</label>
      <input type="password" id="confirmPassword" />

      <label for="phone">Teléfono</label>
      <input type="text" id="phone" />

      <button @click="registrarse">Registrarse</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RegistroForm',
  methods: {
    registrarse() {
      // Aquí iría la lógica real para enviar los datos al backend.
      // Por ahora simplemente redirige.
      this.$router.push('/perfil');
    }
  }
}
</script>

<style scoped>
.registro-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #c0c0c0;
}

.registro-card {
  background-color: #b3b7f0;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 0 5px #999;
  width: 300px;
  text-align: center;
}

.registro-card h2 {
  margin-bottom: 20px;
  font-weight: bold;
}

.registro-card label {
  display: block;
  text-align: left;
  margin-top: 10px;
  color: #333;
}

.registro-card input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: #f5e6ff;
}

.registro-card button {
  margin-top: 20px;
  padding: 10px;
  width: 100%;
  background-color: #42c2f5;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  color: black;
}
</style>
