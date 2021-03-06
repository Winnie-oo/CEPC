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
import VaccineDate from "../views/VaccineDate";
import Appoint from "../views/Appoint";
import DateUpdate from "../views/DateUpdate";
import AppointUpdate from "../views/AppointUpdate";
import AddAppoint from "../views/AddAppoint";
import AddDate from "../views/AddDate";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: "Login",
    component: Login,
  },
  {
    path: '/',
    name: "首页",
    component: MyMenu,
    show:true,
    redirect:'/home',
    children: [
      {
        path: '/home',
        name: '统计展示',
        component: Home
      }
    ]
  },
  {
    path: '/',
    name: '用户管理',
    component: MyMenu,
    show:true,
    redirect:'/home',
    children:[
      {
        path: '/users',
        name: '查看用户',
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
    name:"健康填报管理",
    component: MyMenu,
    show:true,
    children:[
      {
        path: '/records',
        name: '查看记录',
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
    path: '/',
    name: '接种预约管理',
    component: MyMenu,
    show:true,
    redirect:'/home',
    children:[
      {
        path: '/vaccineDate',
        name: '日期及余量管理',
        component: VaccineDate
      },
      {
        path: '/appoint',
        name: '预约记录管理',
        component: Appoint
      }
    ]
  },
  {
    path: '/',
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
    path: '/',
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
  {
    path: '/',
    name: "修改预约日期",
    component: MyMenu,
    show:false,
    children: [
      {
        path: '/dateUpdate',
        component: DateUpdate
      }
    ]
  },
  {
    path: '/',
    name: "修改预约记录",
    component: MyMenu,
    show:false,
    children: [
      {
        path: '/appointUpdate',
        component: AppointUpdate
      }
    ]
  },
  {
    path: '/',
    name: "添加预约记录",
    component: MyMenu,
    show:false,
    children: [
      {
        path: '/addAppoint',
        component: AddAppoint
      }
    ]
  },
  {
    path: '/',
    name: "添加预约日期",
    component: MyMenu,
    show:false,
    children: [
      {
        path: '/addDate',
        component: AddDate
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
