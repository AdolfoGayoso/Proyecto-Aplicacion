<template>
  <router-link to="/" class="logo-link">
    <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
  </router-link>
  <div class="registro-container">
    <div class="registro-card">
      <h2>Registro</h2>

      <div class="form-group">
        <label for="rut">RUT</label>
        <input type="text" id="rut" v-model="rut" @input="formatearRut" placeholder="Ej: 12.345.678-9" />
      </div>

      <div class="form-group">
        <label for="email">Correo electrónico</label>
        <input type="email" id="email" v-model="email" />
      </div>

      <div class="form-group">
        <label for="userName">Nombre de usuario</label>
        <input type="text" id="userName" v-model="userName" />
      </div>

      <div class="form-group">
        <label for="password">Contraseña</label>
        <input type="password" id="password" v-model="password" />
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirmar contraseña</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" />
      </div>

      <button class="register-btn" @click="registrarse">Registrarse</button>

      <router-link to="/login" class="login-link">Volver al inicio de sesión</router-link>
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
    formatearRut() {
      let valor = this.rut.replace(/[^0-9kK]/g, '').toUpperCase();
      if (valor.length > 9) valor = valor.slice(0, 9);

      const cuerpo = valor.slice(0, -1);
      const verificador = valor.slice(-1);
      const cuerpoConPuntos = cuerpo.replace(/\B(?=(\d{3})+(?!\d))/g, ".");

      if (cuerpo && verificador) {
        this.rut = `${cuerpoConPuntos}-${verificador}`;
      } else {
        this.rut = valor;
      }
    },

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
          alert("¡Registro exitoso! Valida tu correo para iniciar sesión de forma correcta.");
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
};
</script>

<style scoped>
.registro-container {
  height: 100vh;
  background-color: #9698d6;
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
  width: 100%;
  max-width: 450px;
  text-align: center;
  box-sizing: border-box;
}

h2 {
  color: black;
  margin-bottom: 1.5rem;
}

.form-group {
  text-align: left;
  margin-bottom: 1.2rem;
  box-sizing: border-box;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: black;
  font-weight: 500;
  font-size: 0.9rem;
}

input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: border 0.3s;
  background-color: white;
  box-sizing: border-box;
}

input:focus {
  outline: none;
  border-color: #ddd;
  box-shadow: none;
  background-color: white;
}

input:-webkit-autofill {
  box-shadow: 0 0 0px 1000px white inset !important;
  -webkit-box-shadow: 0 0 0px 1000px white inset !important;
  background-color: white !important;
  color: #000;
}

.register-btn {
  background-color: #9698d6;
  color: white;
  border: none;
  padding: 0.8rem;
  width: 100%;
  border-radius: 8px;
  margin-top: 1rem;
  cursor: pointer;
  font-weight: 600;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.login-link {
  color: #9698d6;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  text-align: center;
}

.login-link:hover {
  text-decoration: underline;
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
</style>
