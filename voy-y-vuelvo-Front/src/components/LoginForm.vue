<template>
  <div class="login-container">
    <h2>Inicio de sesión</h2>
    <label>Correo electrónico</label>
<input type="email" v-model="email" />

<label>Contraseña</label>
<input type="password" v-model="password" />

<button class="login-button" @click="iniciarSesion">Ingresar</button>

<a href="#" class="forgot">¿Olvidó su contraseña?</a>

<hr />
<p>o</p>
<router-link to="/registro">Regístrese aquí</router-link>

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
        
         // Verificar si la respuesta tiene la estructura esperada
        if (!result.data || !result.data.token || !result.data.userType) {
          alert('Respuesta del servidor inesperada')
          return
        }

        const { token, userType } = result.data
        
        // Guardar el token y el tipo de usuario en localStorage
        localStorage.setItem('token', token)
        localStorage.setItem('userType', userType)

        // Redirigir según el tipo de usuario
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
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

body {
  background-color: #adb8f2; /* color extraído de la imagen */
  margin: 0;
  padding: 0;
}

.login-container {
  font-family: 'Poppins', sans-serif;
  width: 300px;
  margin: 100px auto;
  background-color: #adb8f2; /* blanco por dentro */
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  border: 2px solid #8795e6;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #3a3a3a;
  font-weight: 600;
}

label {
  display: block;
  text-align: left;
  margin: 10px 0 5px 20px;
  font-size: 14px;
  color: #333;
}

input {
  font-family: 'Poppins', sans-serif;
  display: block;
  width: 90%;
  margin: 10px auto;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #aaa;
  font-size: 14px;
  transition: border 0.3s ease;
}

input:focus {
  border-color: #6b8eea;
  outline: none;
}

.login-button {
  background-color:  #aaa;
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  margin-top: 10px;
  cursor: pointer;
  color: white;
  font-weight: 600;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #49bfd3;
}

.forgot {
  display: block;
  margin: 10px 0;
  font-size: 13px;
  color: #555;
  text-decoration: none;
}

hr {
  margin: 15px 0;
  border: none;
  border-top: 1px solid #ccc;
}

p {
  font-size: 13px;
  color: #666;
}

.router-link-active,
.router-link-exact-active,
.register {
  color: #4b6cb7;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
}

.register:hover {
  text-decoration: underline;
}
</style>
