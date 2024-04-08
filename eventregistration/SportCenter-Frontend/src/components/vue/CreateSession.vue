<template>
  <div>
    <div>
      <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Sport Center</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
                <b-nav-item to="/admin/viewCustomers" >View Customers</b-nav-item>
                <b-nav-item to="/admin/viewInstructors" href="#">View Instructors</b-nav-item>
                <b-nav-item to="/admin/displayCourse" href="#">View Courses</b-nav-item>
                <b-nav-item to="/admin/displaySessions" href="#">View Sessions</b-nav-item>
            </b-navbar-nav>

          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown right>
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item @click="logout">Sign Out</b-dropdown-item>
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
          <div class="title">Create a Session</div>

          <b-form-group
            id="input-group-1"
            label="Start Time:"
            label-for="input-startTime"
            class="form-group-content"
          >
            <b-form-input
              id="input-startTime"
              v-model="form.startTime"
              placeholder="YYYY-MM-DD HH:mm"
              pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-2"
            label="End Time:"
            label-for="input-endTime"
            class="form-group-content"
          >
            <b-form-input
              id="input-endTime"
              v-model="form.endTime"
              placeholder="YYYY-MM-DD HH:mm"
              pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-3"
            label="Course Name:"
            label-for="input-courseName"
            class="form-group-content"
          >
            <b-form-input
              id="input-courseName"
              v-model="form.courseName"
              placeholder="Enter course name"
              required
            ></b-form-input>
          </b-form-group>

            <b-form-group
              id="input-group-4"
              label="Location Name:"
              label-for="input-locationName"
              class="form-group-content"
            >
              <b-form-input
                id="input-locationName"
                v-model="form.locationName"
                placeholder="Enter location name"
                required
              ></b-form-input>
            </b-form-group>

          <div class="buttons-container">
            <b-button @click="onReturn" variant="info">Return</b-button>
            <b-button type="reset" variant="info">Reset</b-button>
            <b-button type="submit" variant="primary">Create</b-button>
          </div>
          <p v-if="error" style="color: red">{{ error }}</p>
          <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
        </b-form>
      </b-card>
    </div>
  </div>
</template>

<script src="../javascript/CreateSession.js"></script>

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
