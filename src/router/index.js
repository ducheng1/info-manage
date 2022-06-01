import Vue from 'vue'
import VueRouter from 'vue-router'
import SysUser from '../views/SysUser.vue'
import SysDept from "../views/SysDept.vue"

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
  },
  {
    path: "/dept",
    name: "dept",
    component: SysDept
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
