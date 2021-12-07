import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import Main from '@/components/Main.vue';
import Login from '@/components/Login.vue';
import Profile from '@/components/Profile.vue';
import Mypage from '@/components/Mypage.vue';
import Register from '@/components/Register.vue';
import Building from '@/components/building/Building2.vue';

import Notice from '@/components/notice/Notice.vue';

import Board from '@/components/board/Board.vue'

const routes = [
  {
    name: "Login",
    path: "/",
    component: Login,
  },
  {
    name: "Main",
    path: "/Main",
    component: Main,
  }, 
  {
    name: "Profile",
    path: "/Profile",
    component: Profile,
  },
  {
    name: "Mypage",
    path: "/Mypage",
    component: Mypage,
  },
  {
    name: 'Register',
    path: '/register',
    component: Register
  },
  {
    name: 'Building',
    path: '/building',
    component: Building
  },
  {
    name: 'Board',
    path: '/board',
    component: Board
  },
  {
    name: 'Notice',
    path: '/notice',
    component: Notice
  },
  
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
