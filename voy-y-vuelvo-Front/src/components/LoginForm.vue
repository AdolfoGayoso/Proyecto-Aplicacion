<template>
  <router-link to="/" class="logo-link">
    <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
  </router-link>

  <div class="login-container">
    <div class="login-card">
      <h2>Inicio de sesión</h2>

      <div class="form-group">
        <label for="email">Correo electrónico</label>
        <input type="email" id="email" v-model="email" />
      </div>

      <div class="form-group">
        <label for="password">Contraseña</label>
        <input type="password" id="password" v-model="password" />
      </div>

      <button class="login-btn" @click="iniciarSesion">Ingresar</button>

      <a href="#" class="forgot">¿Olvidó su contraseña?</a>

      <hr />
      <p>o</p>
      <router-link to="/registro" class="register-link">Regístrese aquí</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',

  data() {
    return {
      email: '',
      password: ''
    }
  },

  created() {
    const token = localStorage.getItem('token');
    if (token) {
      // Ya existe un token: redirige al perfil
      this.$router.push('/perfil');
    }
  },

  methods: {
    async iniciarSesion() {
      try {
        const response = await fetch('http://localhost:8080/api/auth/user/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        })

        const result = await response.json();

        if (!response.ok) {
          alert('Error al iniciar sesión: ' + (result.message || 'desconocido'));
          return;
        }

        const { token, userType } = result.data;

        localStorage.setItem('token', token);
        localStorage.setItem('userType', userType);

        if (userType === 'PUBLISHER') {
          this.$router.push('/publisher-dashboard');
        } else {
          this.$router.push('/perfil');
        }

      } catch (error) {
        console.error('Error en la petición:', error);
        alert('Ocurrió un error al intentar iniciar sesión: ' + error.message);
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  background-color: #9698d6;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}

.login-card {
  background-color: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 450px;
  text-align: center;
  box-sizing: border-box;
}

.form-group {
  text-align: left;
  margin-bottom: 1.2rem;
  box-sizing: border-box;
}

h2 {
  color: black;
  margin-bottom: 1.5rem;
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
  background-color: white;
  box-sizing: border-box;
}

input:focus {
  outline: none;
  border-color: #ddd;
  box-shadow: none;
}

input:-webkit-autofill {
  box-shadow: 0 0 0px 1000px white inset !important;
  -webkit-box-shadow: 0 0 0px 1000px white inset !important;
  background-color: white !important;
  color: #000;
}

.login-btn {
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


.forgot {
  display: block;
  margin: 10px 0;
  font-size: 0.85rem;
  color: black;
  text-decoration: none;
}

.forgot:hover {
  text-decoration: underline;
}

hr {
  margin: 15px 0;
  border: none;
  border-top: 1px solid #ccc;
}

p {
  font-size: 0.85rem;
  color: black;
  margin: 0.5rem 0;
  text-align: center;
}

.register-link {
  color: #9698d6;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 500;
}

.register-link:hover {
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
