<template>
  <div>
    <div>
      <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Sport Center</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
                <b-nav-item to="/customer/displayCourse" href="#">View Courses</b-nav-item>
                <b-nav-item to="/customer/displaySessions" href="#">View Sessions</b-nav-item>
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
    </div>
    <div class="form-container">
      <b-card>
        <b-form
          @submit.prevent="onSubmit"
          @reset.prevent="onReset"
          v-if="show"
          class="form-content"
        >
          <div class="title">Filtering Sessions</div>

          <b-form-group
            id="input-group-2"
            label="Course Name:"
            label-for="input-name"
            class="form-group-content"
          >
            <b-form-input
              id="input-name"
              v-model="form.name"
              placeholder="Enter Course Name"
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-3"
            label="Date:"
            label-for="input-date"
            class="form-group-content"
          >
            <b-form-input
              id="input-date"
              v-model="form.date"
              placeholder="Enter Date (YYYY-MM-DD)"
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-4"
            label="Duration:"
            label-for="input-duration"
            class="form-group-content"
          >
            <b-form-input
              id="input-duration"
              v-model="form.duration"
              placeholder="Enter Duration"
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-5"
            label="Instructor:"
            label-for="input-instructor"
            class="form-group-content"
          >
            <b-form-input
              id="input-instructor"
              v-model="form.instructor"
              placeholder="Enter Instructor Name"
            ></b-form-input>
          </b-form-group>

          <div class="buttons-container">
            <b-button @click="onReturn" variant="info">Return</b-button>
            <b-button type="reset" variant="info">Reset</b-button>
            <b-button type="submit" variant="primary">Filter</b-button>
          </div>
          <p v-if="error" style="color: red">{{ error }}</p>
          <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
        </b-form>
      </b-card>
    </div>
  </div>
</template>

<script src="../javascript/FilterSessionsCustomer.js"></script>

<style>
.form-container {
  max-width: 600px;
  margin: auto;
}

.form-content {
  display: flex;
  flex-direction: column;
  align-items: center;
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
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-top: 15px;
  margin-bottom: 15px;
}
</style>
