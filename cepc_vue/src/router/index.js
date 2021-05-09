import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Records from "../views/Records";
import MyMenu from '../views/MyMenu.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '社区用户',
    component: MyMenu,
    children:[
      {
        path: '/home',
        name: 'Home',
        component: Home
      },
      {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
      }
    ]
  },
  {
    path: '/',
    name:"健康打卡",
    component: MyMenu,
    children:[
      {
        path: '/Records',
        name: 'Record',
        component: Records
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
