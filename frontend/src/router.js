import { createRouter, createWebHistory } from 'vue-router';

import Index from './components/Index.vue';
import Login from './components/auth/Login.vue';
import Signup from './components/auth/Signup.vue';
import DashboardLayout from './components/home/DashboardLayout.vue';

import Home from './components/home/Home.vue';
import QuestionnaireTesting from './components/home/tests/QuestionnaireTesting.vue';
import BehavioralVideoTesting from './components/home/tests/BehavioralVideoTesting.vue';
import Therapy from './components/home/Therapy.vue';
import GameHome from './components/home/games/GameHome.vue';
import VideoChatHome from './components/home/chat/VideoChatHome.vue';
import DoctorSuggestion from './components/home/doctors/DoctorSuggestion.vue';
import TestResultQ from './components/home/tests/TestResultQ.vue';
import TestResultVideo from './components/home/tests/TestResultVideo.vue';

const routes = [
  { path: '/', component: Index },
  { path: '/login', component: Login },
  { path: '/signup', component: Signup },
  {
    path: '/dashboard',
    component: DashboardLayout, // This will act as the common layout
    props: true,
    children: [
      {
        path: '',
        redirect: '/dashboard/home', // Redirect to a default child route (optional)
      },
      {path: 'home', component: Home},
      { path: 'test-q', component: QuestionnaireTesting },
      { path: 'test-video', component: BehavioralVideoTesting },
      { path: 'q-test-result', component: TestResultQ, props: true },
      { path: 'video-test-result', component: TestResultVideo, props: true },
      { path: 'therapy', component: Therapy },
      { path: 'game', component: GameHome },
      { path: 'doctors', component: DoctorSuggestion },
      { path: 'chat', component: VideoChatHome },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
