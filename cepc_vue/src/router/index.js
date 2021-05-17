import Vue from 'vue'
import VueRouter from 'vue-router'
import MyMenu from '../views/MyMenu.vue'
import Users from "../views/Users";
import Records from "../views/Records";
import AddUser from '../views/AddUser.vue'
import AddRecord from '../views/AddRecord.vue'
import UserUpdate from '../views/UserUpdate.vue'
import RecordUpdate from "../views/RecordUpdate";

import Login from "../views/Login";
import Home from "../views/Home";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: "Login",
    component: Login,
  },
  {
    path: '/home',
    name: "首页",
    component: MyMenu,
    show:true,
    redirect:'/home',
    children: [
      {
        path: '/home',
        component: Home
      }
    ]
  },
  {
    path: '/home',
    name: '社区用户',
    component: MyMenu,
    show:true,
    redirect:'/home',
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
    path: '/home',
    name:"健康填报",
    component: MyMenu,
    show:true,
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
      },
    ]
  },
  {
    path: '/home',
    name: "修改用户信息",
    component: MyMenu,
    show:false,
    children: [
        {
          path: '/userUpdate',
          component: UserUpdate
        }
    ]
  },
  {
    path: '/home',
    name: "修改填报信息",
    component: MyMenu,
    show:false,
    children: [
      {
        path: '/recordUpdate',
        component: RecordUpdate
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
