import Vue from 'vue'
import VueRouter from 'vue-router'
import SysUser from '../views/SysUser.vue'
import HomeView from "../views/HomeView.vue"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: "/user"
  },
  {
    path: "/user",
    name: "user",
    component: SysUser
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
