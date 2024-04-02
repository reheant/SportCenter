import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/vue/Hello'
import Login from '@/components/vue/Login'
import CreateCustomer from '@/components/vue/CreateCustomer'
import CreateCourse from '@/components/vue/CreateCourse'
import DisplayCourse from '@/components/vue/DisplayCourse'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/createCustomer',
      name: 'CreateCustomer',
      component: CreateCustomer
    },
    {
      path: '/displayCourse',
      name: 'DisplayCourse',
      component: DisplayCourse
    },
    {
      path: '/createCourse',
      name: 'CreateCourse',
      component: CreateCourse
    }
  ]
})
