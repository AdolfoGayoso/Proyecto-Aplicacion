<template>
  <div class="compra-container">
    <router-link to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Logo del Proyecto" class="corner-logo" />
    </router-link>

    <h2>Información del pasajero</h2>

    <div class="formulario">
      <div v-if="!isLoggedIn" class="formulario-publico">
        <div class="form-group">
          <label>RUT</label>
          <input
            type="text"
            v-model="formData.rut"
            @input="formatearRut"
            placeholder="Ej: 12.345.678-9"
          />
        </div>
        <div class="form-group">
          <label>Correo electrónico</label>
          <input type="email" v-model="formData.email" placeholder="Ej: correo@example.com" />
        </div>
        <div class="form-group">
          <label>Confirmar correo electrónico</label>
          <input type="email" v-model="formData.confirmEmail" placeholder="Confirme su correo electrónico" />
        </div>
        <p class="aviso-importante">
          ⚠️ Asegúrese de ingresar correctamente su correo electrónico, ya que ahí se enviarán los pasajes.
        </p>
      </div>
      <div v-else class="formulario-publico">
        <div class="form-group">
          <p><strong>Nombre:</strong> {{ userName }}</p>
          <p><strong>RUT:</strong> {{ userRut }}</p>
          <p><strong>Correo:</strong> {{ userEmail }}</p>
        </div>
      </div>
    </div>

    <h3>Opciones de pago</h3>
    <div class="metodos-pago">
      <label class="metodo">
        <input type="radio" v-model="metodoPago" value="webpay" />
        <img src="@/assets/webpay.png" alt="Webpay" />
      </label>
      <label class="metodo">
        <input type="radio" v-model="metodoPago" value="paypal" />
        <img src="@/assets/paypal.png" alt="PayPal" />
      </label>
      <label class="metodo">
        <input type="radio" v-model="metodoPago" value="mercadopago" />
        <img src="@/assets/mercadopago.png" alt="MercadoPago" />
      </label>
    </div>

    <div class="terminos">
      <label>
        <input type="checkbox" v-model="aceptaTerminos" />
        <span @click="mostrarTerminos" class="enlace-terminos">Acepto términos y condiciones</span>
      </label>
    </div>

    <button class="btn-pagar" @click="enviarCompra" :disabled="!aceptaTerminos">PAGAR</button>

    <div v-if="mostrarModal" class="modal">
      <div class="modal-contenido">
        <span class="cerrar" @click="mostrarModal = false">&times;</span>
        <h3>Términos y Condiciones</h3>
        <p>Al realizar la compra de un pasaje a través de nuestra plataforma "Voy y Vuelvo", usted acepta las siguientes condiciones:</p>
        <ul>
          <li>Los datos ingresados deben ser verídicos y corresponder al pasajero que utilizará el servicio.</li>
          <li>Una vez confirmada la compra, el pasaje no es reembolsable, salvo en los casos establecidos por la ley.</li>
          <li>Es responsabilidad del pasajero presentarse con anticipación en el punto de embarque.</li>
          <li>El uso indebido del sistema puede llevar a la cancelación de la compra sin derecho a devolución.</li>
          <li>Los datos personales se almacenarán y tratarán conforme a la ley N°19.628 sobre protección de la vida privada.</li>
        </ul>
        <p>Para más detalles, puede contactarnos a través de nuestro formulario de atención en el sitio web.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CompraView',
  data() {
    return {
      formData: {
        rut: '',
        email: '',
        confirmEmail: ''
      },
      metodoPago: '',
      aceptaTerminos: false,
      isLoggedIn: false,
      userEmail: '',
      userRut: '',
      userName: '',
      mostrarModal: false
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
  async mounted() {
    const token = localStorage.getItem('token');
    if (token) {
      this.isLoggedIn = true;
      const parsed = this.parseJwt(token);
      this.userEmail = parsed?.sub || '';

      try {
        const response = await fetch('http://localhost:8080/api/user/info', {
          headers: { Authorization: `Bearer ${token}` }
        });
        if (response.ok) {
          const data = await response.json();
          this.userName = data.data.userName;
          this.userRut = this.formatearRutVista(data.data.rut);
        }
      } catch (err) {
        console.error('Error al obtener datos del usuario:', err);
      }
    }
  },
  methods: {
    mostrarTerminos() {
      this.mostrarModal = true;
    },
    formatearRut() {
      let valor = this.formData.rut.replace(/[^0-9kK]/g, '').toUpperCase();
      if (valor.length > 9) valor = valor.slice(0, 9);

      const cuerpo = valor.slice(0, -1);
      const verificador = valor.slice(-1);
      const cuerpoConPuntos = cuerpo.replace(/\B(?=(\d{3})+(?!\d))/g, ".");

      if (cuerpo && verificador) {
        this.formData.rut = `${cuerpoConPuntos}-${verificador}`;
      } else {
        this.formData.rut = valor;
      }
    },
    formatearRutVista(rutPlano) {
      let valor = rutPlano.replace(/[^0-9kK]/g, '').toUpperCase();
      const cuerpo = valor.slice(0, -1);
      const verificador = valor.slice(-1);
      const cuerpoConPuntos = cuerpo.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
      return `${cuerpoConPuntos}-${verificador}`;
    },
    async enviarCompra() {
      if (!this.isLoggedIn && this.formData.email !== this.formData.confirmEmail) {
        alert('Los correos electrónicos no coinciden.');
        return;
      }

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
};
</script>

<style scoped>
.compra-container {
  background-color: #9698d6;
  min-height: 100vh;
  padding: 30px 20px;
  font-family: 'Segoe UI', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
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
.formulario-publico {
  background-color: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
  margin-bottom: 20px;
  text-align: left;
}
.form-group {
  margin-bottom: 15px;
}
input[type="text"],
input[type="email"] {
  width: 96%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
}
.metodos-pago label {
  display: block;
  margin-bottom: 10px;
  color: white;
}
.terminos {
  margin: 15px 0;
  color: white;
}
.enlace-terminos {
  color: #f0f0f0;
  text-decoration: underline;
  cursor: pointer;
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
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
}
.modal-contenido {
  background: white;
  padding: 30px;
  border-radius: 12px;
  max-width: 500px;
  text-align: left;
  position: relative;
}
.cerrar {
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 24px;
  cursor: pointer;
}
.metodos-pago {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}
.metodo {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  margin-left: 50px;
}
.metodo img {
  width: 150px;
  margin-top: 5px;
  margin-left: 5px;
}
.aviso-importante {
  color: #b10000;
  font-weight: bold;
  font-size: 0.9rem;
  margin-top: 10px;
}
</style>
