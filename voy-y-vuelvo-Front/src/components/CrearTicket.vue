<template>
  <div class="crear-ticket-container">
    <router-link to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
    </router-link>

    <div class="ticket-form">
      <h2>Crear Ticket de Soporte</h2>

      <div class="form-group">
        <label for="titulo">Título</label>
        <input type="text" id="titulo" v-model="title" disabled />
      </div>

      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="descripcion" rows="6" placeholder="Describe el problema con tu viaje..."></textarea>
      </div>

      <button class="enviar-btn" @click="enviarTicket">Enviar Ticket</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CrearTicket',
  data() {
    return {
      title: 'Reclamo de viaje',
      descripcion: '',
      viaje: {
        purchaseUuid: this.$route.params.id
      }
    };
  },
  methods: {
    async enviarTicket() {
      if (!this.descripcion.trim()) {
        alert('Por favor ingresa una descripción del problema.');
        return;
      }

      const token = localStorage.getItem('token');
      if (!token) {
        alert('Debes iniciar sesión para enviar un ticket.');
        return;
      }

      const ticket = {
        purchaseUuid: this.viaje.purchaseUuid,
        title: this.title,
        description: this.descripcion
      };

      try {
        const response = await fetch('http://localhost:8080/api/ticket/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify(ticket)
        });

        const result = await response.json();
        if (!response.ok) {
          throw new Error(result.message || 'Error al enviar el ticket');
        }

        alert('Ticket enviado correctamente');
        this.$router.push('/perfil');
      } catch (error) {
        alert('Error al enviar el ticket: ' + error.message);
      }
    }
  }
};
</script>

<style scoped>
.crear-ticket-container {
  padding: 2rem;
  max-width: 600px;
  margin: 0 auto;
}

.ticket-form {
  background: #f4f4f4;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 0 10px #ccc;
}

.form-group {
  margin-bottom: 1.5rem;
}

textarea,
input {
  width: 100%;
  padding: 0.75rem;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.enviar-btn {
  background-color: #5c6bc0;
  color: white;
  border: none;
  padding: 0.75rem 2rem;
  border-radius: 6px;
  cursor: pointer;
}
</style>
