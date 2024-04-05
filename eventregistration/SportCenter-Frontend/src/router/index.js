import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/vue/Login'
import CreateCustomer from '@/components/vue/CreateCustomer'
import CreateCourse from '@/components/vue/CreateCourse'
import DisplayCourseAdmin from "@/components/vue/DisplayCourseAdmin";
import DisplayCourseInstructor from "@/components/vue/DisplayCourseInstructor";
import SelectPaymentType from '@/components/vue/SelectPaymentType'
import AddPaypal from '@/components/vue/AddPaypal'
import AddCard from '@/components/vue/AddCard'
import Admin from '@/components/vue/Admin'
import ViewAccounts from '@/components/vue/ViewAccounts'
import ViewInstructors from '@/components/vue/ViewInstructors'
Vue.use(Router)


export default new Router({
  routes: [
    {
      path: "/login",
      name: "Login",
      component: Login,
    },
    {
      path: "/createCustomer",
      name: "CreateCustomer",
      component: CreateCustomer,
    },
    {
      path: "/admin/displayCourse",
      name: "DisplayCourseAdmin",
      component: DisplayCourseAdmin,
    },
    
    {
      path: '/addPaypal',
      name: 'AddPaypal',
      component: AddPaypal
    },
    {
      path: '/addCard',
      name: 'AddCard',
      component: AddCard
    },
    {
      path: '/selectPaymentType',
      name: 'selectPaymentType',
      component: SelectPaymentType
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin
    },
    {
      path: '/admin/viewCustomers',
      name: 'viewAccounts',
      component: ViewAccounts
    },
    {
      path: '/admin/viewInstructors',
      name: 'viewInstructors',
      component: ViewInstructors
    },
    {
      path: "/instructor/displayCourse",
      name: "DisplayCourseInstructor",
      component: DisplayCourseInstructor,
    },
    {
      path: "/admin/createCourse",
      name: "CreateCourse",
      component: CreateCourse,
    },
    {
      path: "/instructor/createCourse",
      name: "CreateCourse",
      component: CreateCourse,
    },
  ]
})

