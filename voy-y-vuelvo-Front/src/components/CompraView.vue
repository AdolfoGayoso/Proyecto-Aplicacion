<template>
  <div class="compra-container">
    <div class="paso-indicador">
      <span class="paso activo">1</span>
      <span>→</span>
      <span class="paso actual">2</span>
      <span>→</span>
      <span class="paso">3</span>
    </div>

    <h2>Información de los pasajeros</h2>

    <div class="formulario">
      <div v-if="!isLoggedIn">
        <div class="form-group">
          <label>RUT</label>
          <input type="text" v-model="formData.rut" placeholder="Ej: 12345678-9" />
        </div>
        <div class="form-group">
          <label>Correo electrónico</label>
          <input type="email" v-model="formData.email" placeholder="Ej: correo@example.com" />
        </div>
      </div>
      <div v-else>
        <p><strong>Usuario logeado:</strong> {{ userEmail }}</p>
      </div>
    </div>

    <h3>Opciones de pago</h3>
    <div class="metodos-pago">
      <label><input type="radio" v-model="metodoPago" value="webpay" /> Webpay</label>
      <label><input type="radio" v-model="metodoPago" value="paypal" /> PayPal</label>
    </div>

    <div class="terminos">
      <label><input type="checkbox" v-model="aceptaTerminos" /> Acepto términos y condiciones</label>
    </div>

    <button class="btn-pagar" @click="enviarCompra" :disabled="!aceptaTerminos">PAGAR</button>
  </div>
</template>

<script>
export default {
  name: 'CompraView',
  data() {
    return {
      formData: {
        rut: '',
        email: ''
      },
      metodoPago: '',
      aceptaTerminos: false,
      isLoggedIn: false,
      userEmail: ''
    };
  },
  computed: {
    tripId() {
      return parseInt(this.$route.query.tripId);
    },
    stopIdFrom() {
      return parseInt(this.$route.query.stopIdFrom);
    },
    stopIdTo() {
      return parseInt(this.$route.query.stopIdTo);
    }
  },
  mounted() {
    const token = localStorage.getItem('token');
    if (token) {
      this.isLoggedIn = true;
      this.userEmail = this.parseJwt(token)?.sub || 'Usuario registrado';
    }
  },
  methods: {
    async enviarCompra() {
      const token = localStorage.getItem('token');
      const payload = {
        tripId: this.tripId,
        stopIdFrom: this.stopIdFrom,
        stopIdTo: this.stopIdTo,
        rut: this.isLoggedIn ? null : this.formData.rut,
        email: this.isLoggedIn ? null : this.formData.email
      };

      try {
        const res = await fetch('http://localhost:8080/api/ticket/purchase', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            ...(token ? { Authorization: `Bearer ${token}` } : {})
          },
          body: JSON.stringify(payload)
        });

        if (!res.ok) {
          const err = await res.json();
          alert('Error en la compra: ' + (err.message || 'desconocido'));
        } else {
          alert('Compra realizada con éxito');
          this.$router.push('/confirmacion');
        }
      } catch (err) {
        alert('Error al conectar con el servidor: ' + err.message);
      }
    },
    parseJwt(token) {
      try {
        return JSON.parse(atob(token.split('.')[1]));
      } catch (e) {
        return null;
      }
    }
  }
}
</script>

<style scoped>
.compra-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  font-family: 'Segoe UI', sans-serif;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.1);
}

.paso-indicador {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  font-weight: bold;
  color: #888;
}

.paso {
  width: 30px;
  height: 30px;
  background: #ccc;
  border-radius: 50%;
  text-align: center;
  line-height: 30px;
}

.activo {
  background: #3f468c;
  color: #fff;
}

.actual {
  background: #f86363;
  color: #fff;
}

.formulario {
  margin: 20px 0;
}

.form-group {
  margin-bottom: 15px;
}

input[type="text"],
input[type="email"] {
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
}

.metodos-pago label {
  display: block;
  margin-bottom: 10px;
}

.terminos {
  margin: 15px 0;
}

.btn-pagar {
  background-color: #f86363;
  color: #fff;
  padding: 12px 25px;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.btn-pagar:disabled {
  background-color: #ccc;
}
</style>
