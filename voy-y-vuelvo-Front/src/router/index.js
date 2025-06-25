import { createRouter, createWebHistory } from 'vue-router'
import Buscador from '../components/BuscadorPrincipal.vue'
import LoginForm from '../components/LoginForm.vue'
import RegistroForm from '../components/RegistroForm.vue'
import PerfilUsuario from '../components/PerfilUsuario.vue'
import PublisherDashboard from '../components/IndexPublisher.vue'
import Resultados from '../components/Resultados.vue'
import CompraView from '../components/CompraView.vue'

const routes = [
  { path: '/', component: Buscador },
  { path: '/login', component: LoginForm },
  { path: '/registro', component: RegistroForm },
  { path: '/perfil', component: PerfilUsuario },
  { path: '/resultados', component: Resultados },
  {
    path: '/publisher-dashboard',
    component: PublisherDashboard,
    meta: { requiresAuth: true, requiredRole: 'PUBLISHER' }
  },
  { path: '/compra', component: CompraView },
  { path: '/crear-ticket/:id', component: () => import('../components/CrearTicket.vue') },

  // ✅ Nueva vista de tickets del usuario
  { path: '/mis-tickets', component: () => import('../components/MisTickets.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
