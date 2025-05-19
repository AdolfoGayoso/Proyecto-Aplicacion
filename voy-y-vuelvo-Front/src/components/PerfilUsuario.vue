<template>
  <div class="perfil-container"  v-if="user">
    <button class="cerrar-btn" @click="cerrarSesion">Cerrar sesión</button>

    <div class="perfil-box">
      <p class="bienvenida">Bienvenid@ {{ user.userName || '' }}!</p>
      <div class="datos-usuario" v-if="user">
        <h2>Datos del Usuario</h2>
        <p>Nombre: {{ user?.userName || 'Nombre no disponible' }}</p>
        <p>RUT: {{ user.rut || 'Sin RUT' }}</p>
        <p>{{ user.email }}</p>
      </div>
      <div class="resumen">
        <h3>Resumen</h3>
        <p>N viajes realizados</p>
        <p>N Tickets realizados</p>
        <button class="tickets-btn">Mis tickets</button>
      </div>
    </div>

    <div class="historial">
      <h2>Historial de viajes</h2>
      <div class="tabla">
        <div class="fila encabezado">
          <span>Origen - Destino</span>
          <span>Fecha</span>
          <span>Empresa</span>
          <span>Precio</span>
        </div>
        <div class="fila" v-for="n in 5" :key="n">
          <span>Origen - Destino</span>
          <span>Fecha</span>
          <span>Empresa</span>
          <span>Precio</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PerfilUsuario',
  data() {
    return {
      user: null
    };
  },
  created() {
    this.obtenerDatosUsuario();
  },
  methods: {
    async obtenerDatosUsuario() {
      const token = localStorage.getItem('token');
       if (!token) {
        alert('No hay sesión activa.');
        this.$router.push('/login');
        return;
      }
       try {
        const response = await fetch('http://localhost:8080/api/auth/user-info',{
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error('Error al obtener los datos del usuario');
        }
         const data = await response.json();
         this.user = data;
         console.log('Datos del usuario:', this.user);
      } catch (error) {
        console.error('Error al obtener los datos del usuario:', error)
      }
  },
    cerrarSesion() {
      localStorage.removeItem('token');
      this.$router.push('/login');
    }
  }
}
</script>


<style scoped>
.perfil-container {
  background-color: #c0c0c0;
  padding: 20px;
  font-family: sans-serif;
  height: 100vh;
}

.cerrar-btn {
  float: right;
  padding: 8px 12px;
  border-radius: 10px;
  background-color: #b3b7f0;
  border: none;
  cursor: pointer;
}

.perfil-box {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.datos-usuario {
  background-color: #b3b7f0;
  padding: 15px;
  border-radius: 20px;
  min-width: 200px;
  text-align: center;
}

.bienvenida {
  font-size: 1.5em;
  font-weight: bold;
  margin: 0 0 10px 10px;
  color: #fff;
}

.resumen {
  background-color: #d1d3ff;
  padding: 15px;
  border-radius: 20px;
  text-align: center;
}

.tickets-btn {
  background-color: #f5e6ff;
  border: none;
  margin-top: 10px;
  padding: 8px;
  border-radius: 10px;
  cursor: pointer;
}

.historial {
  margin-top: 40px;
  background: linear-gradient(to bottom right, #b3b7f0, #7d82d1);
  border-radius: 20px;
  padding: 20px;
}

.tabla {
  margin-top: 10px;
}

.fila {
  display: flex;
  justify-content: space-between;
  background-color: #f5e6ff;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 15px;
}

.encabezado {
  font-weight: bold;
}
</style>
