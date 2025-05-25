<template>
  <div class="perfil-container" v-if="user">
    <button class="cerrar-btn" @click="cerrarSesion">Cerrar sesión</button>

    <!-- Bienvenida encima del cuadro -->
    <p class="bienvenida">Bienvenid@, {{ user.userName || '' }}!</p>

    <div class="perfil-box">
      <div class="datos-usuario">
        <h2>Datos del Usuario</h2>
        <p><strong>Nombre:</strong> {{ user?.userName || 'Nombre no disponible' }}</p>
        <p><strong>RUT:</strong> {{ user.rut || 'Sin RUT' }}</p>
        <p><strong>Correo:</strong> {{ user.email }}</p>
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
        const response = await fetch('http://localhost:8080/api/auth/user-info', {
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
        console.error('Error al obtener los datos del usuario:', error);
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
  background-color: #e8eaf6;
  padding: 20px;
  font-family: 'Segoe UI', sans-serif;
  min-height: 100vh;
  position: relative;
}

.cerrar-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 10px 16px;
  border-radius: 10px;
  background-color: #7986cb;
  color: white;
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.bienvenida {
  font-size: 1.5rem;
  font-weight: 600;
  color: #3f51b5;
  margin-bottom: 2rem;
  text-align: center;
}

.perfil-box {
  display: flex;
  gap: 20px;
  justify-content: center;
  flex-wrap: wrap;
  margin-top: 20px;
}

.datos-usuario,
.resumen {
  background-color:#adb8f2 ;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.08);
  min-width: 300px;
  max-width: 400px;
  flex: 1;
}

.resumen {
  background-color:#adb8f2 ;        
}

.tickets-btn {
  background-color:  #f0f2ff;
  border: none;
  margin-top: 10px;
  padding: 10px 16px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.3s;
}

.tickets-btn:hover {
  background-color: #9fa8da;
}

.historial {
  margin-top: 40px;
  background: linear-gradient(to bottom right, #c5cae9, #9fa8da);
  border-radius: 20px;
  padding: 20px;
}

.tabla {
  margin-top: 10px;
}

.fila {
  display: flex;
  justify-content: space-between;
  background-color: white;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 15px;
}

.encabezado {
  font-weight: bold;
}
</style>
