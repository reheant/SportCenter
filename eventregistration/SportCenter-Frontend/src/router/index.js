import Vue from "vue";
import Router from "vue-router";
import Hello from "@/components/vue/Hello";
import Login from "@/components/vue/Login";
import CreateCustomer from "@/components/vue/CreateCustomer";
import CreateCourse from "@/components/vue/CreateCourse";
import DisplayCourseAdmin from "@/components/vue/DisplayCourseAdmin";
import DisplayCourseInstructor from "@/components/vue/DisplayCourseInstructor";
import DisplaySessionsAdmin from "@/components/vue/DisplaySessionsAdmin";
import ModifySession from "@/components/vue/ModifySession";
import CreateSession from "@/components/vue/CreateSession";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Hello",
      component: Hello,
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
      path: "/admin/displayCourse",
      name: "DisplayCourseAdmin",
      component: DisplayCourseAdmin,
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
    {
      path: "/admin/DisplaySessions",
      name: "DisplaySessions",
      component: DisplaySessionsAdmin,
    },
    {
      path: "/admin/ModifySession",
      name: "ModifySession",
      component: ModifySession,
    },
    {
      path: "/admin/CreateSession",
      name: "CreateSession",
      component: CreateSession,
    },
  ],
});
