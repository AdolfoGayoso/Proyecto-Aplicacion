<template>
  <div class="login-container">
    <div class="login-card">
      <h2>Inicio de sesión</h2>

      <label for="email">Correo electrónico</label>
      <input type="email" id="email" v-model="email" />

      <label for="password">Contraseña</label>
      <input type="password" id="password" v-model="password" />

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

        if (!response.ok) {
          const errorBody = await response.json()
          console.error('Error del servidor:', errorBody)
          alert('Error al iniciar sesión: ' + (errorBody.message || 'desconocido'))
          return
        }

        const result = await response.json()
        console.log('Respuesta del backend:', result)
        
        if (!result.data || !result.data.token || !result.data.userType) {
          alert('Respuesta del servidor inesperada')
          return
        }

        const { token, userType } = result.data
        
        localStorage.setItem('token', token)
        localStorage.setItem('userType', userType)

        if (userType === 'PUBLISHER') {
          this.$router.push('/publisher-dashboard')
        } else {
          this.$router.push('/perfil')
        }

      } catch (error) {
        console.error('Error en la petición:', error)
        alert('Ocurrió un error al intentar iniciar sesión: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  background-color: #9698d6; /* Fondo celeste pastel */
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
  width: 350px;
  text-align: center;
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
  background-color: white; /* siempre blanco */
}

input:focus {
  outline: none;
  border-color: #ddd;
  box-shadow: none;
  background-color: white;
}

/* Para inputs autocompletados */
input:-webkit-autofill {
  box-shadow: 0 0 0px 1000px white inset !important;
  -webkit-box-shadow: 0 0 0px 1000px white inset !important;
  background-color: white !important;
  color: #000;
}

.login-btn {
  background-color: #9698d6; /* Celeste pastel */
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

.login-btn:hover {
  background-color: #9698d6;
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
</style>
