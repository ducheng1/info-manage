import Vue from 'vue'
import VueRouter from 'vue-router'
import SysUser from '../views/SysUser.vue'
import SysDept from "../views/SysDept.vue"
import SysRole from "@/views/SysRole"
import SysMenu from "@/views/SysMenu"
import UserLogin from "@/views/UserLogin";
import Home from "@/views/Home";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: "/login"
    },
    {
        path: "/login",
        name: "login",
        component: UserLogin
    },
    {
        path: "/home",
        meta: "首页",
        component: Home,
        children: [
            {
                path: "/user",
                name: "user",
                component: SysUser
            },
            {
                path: "/dept",
                name: "dept",
                component: SysDept
            },
            {
                path: "/role",
                name: "role",
                component: SysRole
            },
            {
                path: "/menu",
                name: "menu",
                component: SysMenu
            },
        ],
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
