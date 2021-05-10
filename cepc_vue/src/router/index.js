import Vue from 'vue'
import VueRouter from 'vue-router'
import MyMenu from '../views/MyMenu.vue'
import Users from "../views/Users";
import Records from "../views/Records";
import AddUser from '../views/AddUser.vue'
import AddRecord from '../views/AddRecord.vue'
import UserUpdate from '../views/UserUpdate.vue'

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
        name: '用户管理',
        component: Users
      },
      {
        path: '/addUser',
        name: '添加用户',
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
        name: '记录管理',
        component: Records
      },
      {
        path: '/addRecord',
        name: '添加记录',
        component: AddRecord
      }
    ]
  },
  {
    path:'/userUpdate',
    component: UserUpdate
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
