import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/vue/Login";
import CreateCustomer from "@/components/vue/CreateCustomer";
import CreateCourse from "@/components/vue/CreateCourse";
import DisplayCourseAdmin from "@/components/vue/DisplayCourseAdmin";
import DisplayCourseInstructor from "@/components/vue/DisplayCourseInstructor";
import DisplayCourseCustomer from "@/components/vue/DisplayCourseCustomer";
import DisplaySessionsAdmin from "@/components/vue/DisplaySessionsAdmin";
import DisplaySessionsCustomer from "@/components/vue/DisplaySessionsCustomer";
import CreateSession from "@/components/vue/CreateSession";
import SelectPaymentType from '@/components/vue/SelectPaymentType'
import AddPaypal from '@/components/vue/AddPaypal'
import AddCard from '@/components/vue/AddCard'
import Admin from '@/components/vue/Admin'
import ViewAccounts from '@/components/vue/ViewAccounts'
import ViewInstructors from '@/components/vue/ViewInstructors'
import FilterCourseAdmin from '@/components/vue/FilterCourseAdmin'
import FilterCourseInstructor from '@/components/vue/FilterCourseInstructor'
import FilterCourseCustomer from '@/components/vue/FilterCourseCustomer'
import ViewOwners from "@/components/vue/ViewOwners";
import CreateOwner from "@/components/vue/CreateOwner";
import ModifyCourse from "@/components/vue/ModifyCourse";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Landing page",
      redirect: { path: "/login" },
    },
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
      path: "/admin/modify/course/:id",
      name: "Modify Course",
      component: ModifyCourse,
    },
    {
      path: "/admin/displayCourse",
      path: "/admin/displayCourse",
      name: "DisplayCourseAdmin",
      component: DisplayCourseAdmin,
      props: true
    },
    {
      path: "/instructor/displayCourse",
      name: "DisplayCourseInstructor",
      component: DisplayCourseInstructor,
      props: true
    },
    {
      path: "/customer/displayCourse",
      name: "DisplayCourseCustomer",
      component: DisplayCourseCustomer,
      props: true
    },
    {
      path: "/addPaypal",
      name: "AddPaypal",
      component: AddPaypal,
    },
    {
      path: "/addCard",
      name: "AddCard",
      component: AddCard,
    },
    {
      path: "/selectPaymentType",
      name: "selectPaymentType",
      component: SelectPaymentType,
    },
    {
      path: "/admin",
      name: "admin",
      component: Admin,
    },
    {
      path: "/admin/viewCustomers",
      name: "viewAccounts",
      component: ViewAccounts,
    },
    {
      path: "/admin/viewInstructors",
      name: "viewInstructors",
      component: ViewInstructors,
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
    {
      path: "/admin/DisplaySessions",
      name: "DisplaySessionsAdmin",
      component: DisplaySessionsAdmin,
    },
    {
        path: "/customer/DisplaySessions",
        name: "DisplaySessionsCustomer",
        component: DisplaySessionsCustomer,
      },
    {
      path: "/admin/CreateSession",
      name: "CreateSession",
      component: CreateSession,
    },
    {
      path: "/admin/FilterCourses",
      name: "FilterCourses",
      component: FilterCourseAdmin,
    },
    {
      path: '/instructor/FilterCourses',
      name: "FilterCourses",
      component: FilterCourseInstructor,
    },
    {
      path: '/customer/FilterCourses',
      name: "FilterCourses",
      component: FilterCourseCustomer,
    },
    {
      path: "/admin/viewOwners",
      name: "ViewOwners",
      component: ViewOwners,
    },
    {
      path: "/admin/createAdministrator",
      name: "CreateOwner",
      component: CreateOwner,
    },
  ],
});
