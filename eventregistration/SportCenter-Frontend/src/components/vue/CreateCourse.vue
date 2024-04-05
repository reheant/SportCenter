<template>
  <div>
    <div>
      <b-navbar class="navbar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Sport Center</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item @click="onReturn"
              >View Courses</b-nav-item
            >
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
    <div class="form-container">
      <b-card>
        <b-form
          @submit.prevent="onSubmit"
          @reset.prevent="onReset"
          v-if="show"
          class="form-content"
        >
          <div class="title">Create a Course</div>

          <b-form-group
            id="input-group-2"
            label="Course Name:"
            label-for="input-course-name"
            class="form-group-content"
          >
            <b-form-input
              id="input-course-name"
              v-model="form.courseName"
              placeholder="Enter Course Name"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-3"
            label="Description:"
            label-for="input-description"
            class="form-group-content"
          >
            <b-form-textarea
              id="input-description"
              v-model="form.courseDescription"
              placeholder="Enter Description (Maximum 255 chararcters)"
              placeholder2="(Maximum 255 chararcters)"
              rows="4"
              max-rows="6"
              maxlength="255"
              required
            ></b-form-textarea>
          </b-form-group>

          <b-form-group
            id="input-group-1"
            label="Duration:"
            label-for="input-duration"
            class="form-group-content"
          >
            <b-input-group>
              <b-input-group-prepend is-text>
                <b-icon icon="clock"></b-icon>
              </b-input-group-prepend>
              <b-form-input
                id="input-duration"
                v-model="form.courseDuration"
                type="number"
                placeholder="Enter Duration (minutes)"
                required
                step="5"
                min="0"
              ></b-form-input>
            </b-input-group>
          </b-form-group>
          <b-form-group
            id="input-group-4"
            label="Cost:"
            label-for="input-cost"
            class="form-group-content"
          >
            <b-input-group>
              <b-input-group-prepend is-text
                ><b-icon icon="currency-dollar"></b-icon
              ></b-input-group-prepend>
              <b-form-input
                type="number"
                id="input-cost"
                placeholder="Enter Cost"
                v-model="form.courseCost"
                required
                pattern="\d+(\.\d{2})?"
                step="0.01"
                min="0"
              ></b-form-input>
            </b-input-group>
          </b-form-group>

          <b-form-group
            id="input-group-5"
            label="Requires Instructor:"
            v-slot="{ ariaDescribedby }"
            class="form-group-content"
          >
            <b-form-checkbox v-model="form.requiresInstructor"
              >Requires Instructor
            </b-form-checkbox>
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

<script src="../javascript/CreateCourse.js"></script>

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
