<template>
  <div class="registro-container">
    <div class="registro-card">
      <h2>Registro</h2>

      <label for="rut">RUT</label>
      <input type="text" id="rut" v-model="rut" />

      <label for="email">Correo electrónico</label>
      <input type="email" id="email" v-model="email" />

      <label for="userName">Nombre de usuario</label>
      <input type="text" id="userName" v-model="userName" />

      <label for="password">Contraseña</label>
      <input type="password" id="password" v-model="password" />

      <label for="confirmPassword">Confirmar contraseña</label>
      <input type="password" id="confirmPassword" v-model="confirmPassword" />

      <button @click="registrarse">Registrarse</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RegistroForm',
  data() {
    return {
      rut: '',
      email: '',
      userName: '',
      password: '',
      confirmPassword: ''
    };
  },
  methods: {
    async registrarse() {
      if (this.password !== this.confirmPassword) {
        alert("Las contraseñas no coinciden");
        return;
      }

      const requestBody = {
        rut: this.rut,
        email: this.email,
        userName: this.userName,
        password: this.password
      };

      try {
        const response = await fetch('http://localhost:8080/api/auth/register/customer', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestBody)
        });

        const data = await response.json();
        console.log("Respuesta del backend:", data);

        if (response.ok) {
          alert("¡Registro exitoso!");
          this.$router.push('/login');
        } else {
          alert("Error en el registro: " + (data.message || "Error desconocido"));
        }
      } catch (error) {
        console.error("Error al registrar:", error);
        alert("Ocurrió un error en el registro.");
      }
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
