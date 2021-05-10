import Vue from 'vue'
import VueRouter from 'vue-router'
import MyMenu from '../views/MyMenu.vue'
import Users from "../views/Users";
import Records from "../views/Records";
import AddUser from '../views/AddUser.vue'
import AddRecord from '../views/AddRecord.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '社区用户',
    component: MyMenu,
    redirect:'/records',
    children:[
      {
        path: '/users',
        name: 'Users',
        component: Users
      },
      {
        path: '/add',
        name: 'AddUser',
        component: AddUser
      }
    ]
  },
  {
    path: '/',
    name:"健康打卡",
    component: MyMenu,
    children:[
      {
        path: '/records',
        name: 'Records',
        component: Records
      },
      {
        path: '/addRecord',
        name: 'AddRecord',
        component: AddRecord
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
