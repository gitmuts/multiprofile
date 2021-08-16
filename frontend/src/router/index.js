import Vue from 'vue'
import VueRouter from 'vue-router'
import Dash from '../views/Dash.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  },
  {
    path: '/user_signup/:token',
    name: 'UserSignup',
    component: () => import(/* webpackChunkName: "home" */ '../views/UserSignup.vue')
  },
  {
    path: '/',
    name: 'Dash',
    component: Dash,
    children: [
      {
        path: '/admin',
        name: 'Home',
        component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue')
      },
      {
        path: '/permission',
        name: 'Permission',
        component: () => import(/* webpackChunkName: "admin" */ '../views/admin/Permission.vue')
      },
      {
        path: '/role',
        name: 'Role',
        component: () => import(/* webpackChunkName: "admin" */ '../views/admin/Role.vue')
      },
      {
        path: '/user',
        name: 'User',
        component: () => import(/* webpackChunkName: "admin" */ '../views/admin/User.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
