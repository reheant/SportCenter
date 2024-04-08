<template>
  <div>
   <div>
      <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Sport Center</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/admin/viewCustomers">View Customers</b-nav-item>
            <b-nav-item to="/admin/viewInstructors" href="#">View Instructors</b-nav-item>
            <b-nav-item to="/admin/viewOwners">View Owners</b-nav-item>
            <b-nav-item to="/admin/displayCourse" href="#">View Courses</b-nav-item>
            <b-nav-item to="/admin/displaySessions" href="#">View Sessions</b-nav-item>
            <b-nav-item to="/admin/modify/location" href="#">Modify Location</b-nav-item>

          </b-navbar-nav>

          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown right>
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item @click="onLogout">Sign Out</b-dropdown-item>  
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div class = "modify-locaiton">
  <div class="form-container">
    <b-card>
      <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
        <div class="title">Modify Location</div>

        <b-form-group id="input-group-2" label="Name" label-for="input-name" class="form-group-content">
          <b-form-input id="input-name" v-model="form.name" placeholder="Name" required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="Capacity" label-for="input-capacity" class="form-group-content">
          <b-form-input id="input-capacity" type="number" v-model="form.capacity" placeholder="Capacity"
            required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-5" label="Opening Time:" label-for="input-opening-time"
          class="form-group-content">
          <b-form-timepicker id="input-email" v-model="form.openingTime" placeholder="Time"
            required></b-form-timepicker>
        </b-form-group>

        <b-form-group id="input-group-4" label="Closing Time:" label-for="input-closing-time"
          class="form-group-content">
          <b-form-timepicker id="input-closingTime" v-model="form.closingTime" placeholder="Time"
            required></b-form-timepicker>
        </b-form-group>
        <div class="buttons-container">
          <b-button type="reset" variant="info">Reset</b-button>
          <b-button type="submit" variant="primary">Save</b-button>
        </div>
        <p v-if="error" style="color: red">{{ error }}</p>
        <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
      </b-form>
      <!-- <b-card class="mt-3" header="Form Data Result">
          <pre class="m-0">{{ form }}</pre>
        </b-card> -->
    </b-card>
  </div>
</div>
</template>

<script src="../javascript/ModifyLocation.js"></script>

<style>
.error {
  padding: 10px;

}

.form-container {
  max-width: 600px;
  margin: 0 auto;
}
body {
  padding-top: 56px;
}
.form-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.modify-location{
  max-width: 600px;
  margin: 0 auto;
  text-align: center;

}
.form-group-content {
  width: 100%;
  max-width: 400px;
  margin-bottom: 1rem;
}


.buttons-container {
  display: flex;
  justify-content: space-around;
  width: 100%;
  max-width: 400px;
}

.b-card {
  box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-top: 15px;
  margin-bottom: 15px;
}
.navbar {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000;
}

</style>