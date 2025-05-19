import { createRouter, createWebHistory } from 'vue-router'
import Buscador from '../components/BuscadorPrincipal.vue'
import LoginForm from '../components/LoginForm.vue'
import RegistroForm from '../components/RegistroForm.vue'
import PerfilUsuario from '../components/PerfilUsuario.vue'

const routes = [
  { path: '/', component: Buscador },
  { path: '/login', component: LoginForm },
  { path: '/registro', component: RegistroForm },
  { path: '/perfil', component: PerfilUsuario }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
