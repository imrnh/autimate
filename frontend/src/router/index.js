import { createMemoryHistory, createRouter, createWebHistory } from 'vue-router'

import Index from '../components/Index.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';
import Dashboard from '../components/Dashboard.vue';

const routes = [
  { path: '/', component: Index },
  {path: '/login', component: Login},
  {path: '/signup', component: Signup},
  {path: '/dashboard', component: Dashboard},
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router