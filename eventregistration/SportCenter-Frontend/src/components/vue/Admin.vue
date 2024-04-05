<template>
<div>
  <b-navbar  class="navbar" toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="#">Sport Center</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
          <b-nav-item to="/admin/viewCustomers" >View Customers</b-nav-item>
          <b-nav-item to="/admin/viewInstructors" href="#">View Instructors</b-nav-item>
          <b-nav-item to="/admin/displayCourse" href="#">View Courses</b-nav-item>
          <b-nav-item to="/admin/createCourse" href="#">Create Course</b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown right>
          <template #button-content>
            <em>User</em>
          </template>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>



</template>


<style>
.navbar {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000; 
}

body {
  padding-top: 56px; 
}
</style>