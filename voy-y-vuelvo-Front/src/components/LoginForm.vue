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