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
  height: 100vh;
  background-color: #acb4ec; /* Fondo extraído de la imagen */
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}

.registro-card {
  background-color: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 350px;
  text-align: center;
}

h2 {
  color: #2c3e50;
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: 500;
  font-size: 0.9rem;
  text-align: left;
}

input {
  width: 100%;
  padding: 0.8rem;
  margin-bottom: 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: border 0.3s;
}

input:focus {
  outline: none;
  border-color: #4e73df;
  box-shadow: 0 0 0 2px rgba(78, 115, 223, 0.2);
}

.register-btn {
  background-color: #90caf9; /* Celeste pastel */
  color: white;
  border: none;
  padding: 0.8rem;
  width: 100%;
  border-radius: 8px;
  margin-top: 2rem;
  cursor: pointer;
  font-weight: 600;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.register-btn:hover {
  background-color: #64b5f6;
}
</style>