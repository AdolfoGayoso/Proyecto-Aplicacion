<!-- src/components/InicioSesion.vue -->
<template>
  <div class="fondo">
    <div class="login-box">
      <h2>Inicio de sesión</h2>

      <label>Correo electronico</label>
      <input type="email" placeholder="Correo electrónico" />

      <label>Contraseña</label>
      <input type="password" placeholder="Contraseña" />

      <button>Ingresar</button>

      <a href="#">¿Olvidó su contraseña?</a>
      <hr />
      <a href="#" class="registro">Regístrese aquí</a>
    </div>
  </div>
</template>

<style scoped>
.fondo {
  height: 100vh;
  background-color: #c1c1c1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  background-color: #a0a8ec;
  padding: 30px 20px;
  border-radius: 15px;
  box-shadow: 0 0 5px #444;
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 280px;
  text-align: center;
}

input {
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #444;
}

button {
  background-color: #55c8d2;
  border: none;
  padding: 8px;
  border-radius: 10px;
  cursor: pointer;
}

a {
  font-size: 12px;
  color: #2e2e85;
  text-decoration: underline;
  cursor: pointer;
}

hr {
  border: none;
  border-top: 1px solid #333;
  width: 100%;
}
</style>
