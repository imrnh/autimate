
import { createRouter, createWebHistory } from 'vue-router';

import Index from './components/Index.vue';
import Authentication from './components/auth/Authentication.vue';
import OTP from './components/auth/OTP.vue';
import PasswordReset from './components/auth/PasswordReset.vue';

import AdminLogin from './components/admin/AdminLogin.vue';
import AdminDashboardLayout from './components/admin/AdminDashboardLayout.vue';
import AddDoctor from './components/admin/AddDoctor.vue';
import AllDoctors from './components/admin/AllDoctors.vue';

import DashboardLayout from './components/home/DashboardLayout.vue';
import Home from './components/home/Home.vue';
import QuestionnaireTesting from './components/home/tests/QuestionnaireTesting.vue';
import BehavioralVideoTesting from './components/home/tests/BehavioralVideoTesting.vue';
import GameHome from './components/home/games/GameHome.vue';
import VideoChatHome from './components/home/chat/VideoChatHome.vue';
import DoctorSuggestion from './components/home/doctors/DoctorSuggestion.vue';
import AexTestResult from './components/home/tests/AexTestResult.vue';

import DrawingGame from './components/home/therapy/DrawingGame.vue';

import SocialSkillOptions from './components/home/therapy/SocialSkillOptions.vue';
import TherapyHome from './components/home/therapy/TherapyHome.vue';
import SocialSkillStoryView from './components/home/therapy/SocialSkillStoryView.vue';
import TapGame from './components/home/games/TapGame.vue';
import SnakeGame from './components/home/games/SnakeGame.vue';
import AlphabetFind from './components/home/therapy/funalphabet/AlphabetFind.vue';
import AlphabetLearn from './components/home/therapy/funalphabet/AlphabetLearn.vue';
import FunAlphabetMainMenu from './components/home/therapy/funalphabet/FunAlphabetMainMenu.vue';

const routes = [
  // Public Routes
  { path: '/', component: Index },
  { path: '/auth', component: Authentication },
  { path: '/auth/otp', component: OTP, props: true },
  { path: '/auth/password/reset', component: PasswordReset, props: true },

  // Normal User Dashboard Routes
  {
    path: '/dashboard',
    component: DashboardLayout, // Common layout for dashboard
    meta: { requiresAuth: true, role: 'user' },
    children: [
      { path: '', redirect: '/dashboard/home' }, // Default child route
      { path: 'home', component: Home },
      { path: 'aex/q', component: QuestionnaireTesting },
      { path: 'aex/video', component: BehavioralVideoTesting, props: true },
      { path: 'aex/r', component: AexTestResult, props: true },
      { path: 'therapy', component: TherapyHome },
      { path: 'game', component: GameHome },
      { path: 'doctors', component: DoctorSuggestion },
      { path: 'chat', component: VideoChatHome },

      { path: 'game/tapgame', component: TapGame },
      { path: 'game/snake', component: SnakeGame },


      { path: 'therapy/socialskill', component: SocialSkillOptions },
      {
        path: 'therapy/story/:assetName',
        name: 'SocialSkillStoryView',
        component: SocialSkillStoryView,
        props: true,
      },
      { path: 'therapy/drawing', component: DrawingGame },
      { path: 'therapy/funalphabet/learn', component:  AlphabetLearn},
      { path: 'therapy/funalphabet/find', component: AlphabetFind },
      { path: 'therapy/funalp', component: FunAlphabetMainMenu },


    ],
  },

  // Admin Routes
  { path: '/admin/login', component: AdminLogin },
  {
    path: '/admin',
    component: AdminDashboardLayout, // Common layout for admin dashboard
    meta: { requiresAuth: true, role: 'admin' }, // Protect with admin role
    children: [
      { path: '', redirect: '/admin/home' }, // Default child route
      { path: 'home', component: AllDoctors },
      { path: 'doctors', component: AllDoctors },
      { path: 'doctors/add', component: AddDoctor },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
  return null;
}

router.beforeEach((to, from, next) => {
  // Check if the route requires authentication
  if (to.matched.some(record => record.meta.requiresAuth)) {
    const authRecord = to.matched.find(record => record.meta.requiresAuth);
    const requiredRole = authRecord.meta.role;

    if (requiredRole === 'user') {
      const userToken = getCookie('token'); // Replace 'token' with your actual user token cookie name
      if (userToken) {
        next(); // User is authenticated, proceed to the route
      } else {
        next({ path: '/auth' }); // Redirect to normal user login
      }
    } else if (requiredRole === 'admin') {
      const adminToken = getCookie('admin_token');
      if (adminToken) {
        next(); // Admin is authenticated, proceed to the route
      } else {
        next({ path: '/admin/login' });
      }
    } else { // If role is not defined, deny access
      next({ path: '/' }); // Redirect to home or an error page
    }
  } else {
    next(); // Route does not require authentication, proceed as normal
  }
});

export default router;
