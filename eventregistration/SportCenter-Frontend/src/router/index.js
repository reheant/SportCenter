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
import DisplaySessionsInstructor from "@/components/vue/DisplaySessionsInstructor";
import CreateSession from "@/components/vue/CreateSession";
import SelectPaymentType from '@/components/vue/SelectPaymentType'
import AddPaypal from '@/components/vue/AddPaypal'
import AddCard from '@/components/vue/AddCard'
import Admin from '@/components/vue/Admin'
import ViewAccounts from '@/components/vue/ViewAccounts'
import CreateCourseInstructor from "@/components/vue/CreateCourseInstructor";
import ViewInstructors from "@/components/vue/ViewInstructors";
import ModifyLocation from "@/components/vue/ModifyLocation";
import FilterCourseAdmin from '@/components/vue/FilterCourseAdmin'
import FilterCourseInstructor from '@/components/vue/FilterCourseInstructor'
import FilterCourseCustomer from '@/components/vue/FilterCourseCustomer'
import ViewOwners from "@/components/vue/ViewOwners";
import CreateOwner from "@/components/vue/CreateOwner";
import ModifyCourse from "@/components/vue/ModifyCourse";
import ModifyCustomer from "@/components/vue/ModifyCustomer";
import ModifyInstructor from "@/components/vue/ModifyInstructor";
import FilterSessionsAdmin from '@/components/vue/FilterSessionsAdmin'
import FilterSessionsInstructor from '@/components/vue/FilterSessionsInstructor'
import FilterSessionsCustomer from '@/components/vue/FilterSessionsCustomer'
Vue.use(Router)


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
      path: "/admin/modify/location",
      name: "Modify Location",
      component: ModifyLocation,

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
      path: "/admin/modify/customer/:email",
      name: "Modify Customer",
      component: ModifyCustomer,
    },
    {
      path: "/admin/modify/instructor/:email",
      name: "Modify Instructor",
      component: ModifyInstructor,
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
      name: "CreateCourseInstructor",
      component: CreateCourseInstructor,
    },
    {
      path: "/admin/DisplaySessions",
      name: "DisplaySessionsAdmin",
      component: DisplaySessionsAdmin,
      props: true
    },
    {
      path: "/instructor/DisplaySessions",
      name: "DisplaySessionsInstructor",
      component: DisplaySessionsInstructor,
      props: true
    },
    {
      path: "/customer/DisplaySessions",
      name: "DisplaySessionsCustomer",
      component: DisplaySessionsCustomer,
      props: true
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
    {
      path: '/admin/FilterSessions',
      name: "FilterSessions",
      component: FilterSessionsAdmin,
    },
    {
      path: '/instructor/FilterSessions',
      name: "FilterSessions",
      component: FilterSessionsInstructor,
    },
    {
      path: '/customer/FilterSessions',
      name: "FilterSessions",
      component: FilterSessionsCustomer,
    }
  ],
});
