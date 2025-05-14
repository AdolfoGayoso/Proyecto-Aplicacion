<template>
  <div class="login-container">
    <h2>Inicio de sesión</h2>
    <label>Correo electronico</label>
    <input type="email" />
    <label>Contraseña</label>
    <input type="password" />
    <button class="login-button">Ingresar</button>

    <a href="#" class="forgot">¿Olvidó su contraseña?</a>

    <hr />
    <p>o</p>
    <router-link to="/registro">Regístrese aquí</router-link>
  </div>
</template>

<style scoped>
.login-container {
  width: 300px;
  margin: 100px auto;
  background-color: #aab4f3;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  border: 2px solid #6c7cc6;
}
input {
  display: block;
  width: 90%;
  margin: 10px auto;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #888;
}
.login-button {
  background-color: #5cd4e6;
  padding: 8px 20px;
  border: none;
  border-radius: 6px;
  margin-top: 10px;
  cursor: pointer;
}
.forgot {
  display: block;
  margin: 10px 0;
  font-size: 13px;
  color: #333;
}
.register {
  color: blue;
  text-decoration: none;
  font-size: 14px;
}
</style>
