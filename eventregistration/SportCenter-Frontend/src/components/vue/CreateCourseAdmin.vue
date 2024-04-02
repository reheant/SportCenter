<template>
  <div class="form-container">
    <b-card>
      <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" v-if="show" class="form-content">
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
          <b-form-input
            id="input-description"
            v-model="form.courseDescription"
            placeholder="Enter Description"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-1"
          label="Duration:"
          label-for="input-duration"
          class="form-group-content"
        >
          <b-form-input
            id="input-duration"
            v-model="form.courseDuration"
            type="text"
            placeholder="Enter Duration"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-4"
          label="Cost:"
          label-for="input-cost"
          class="form-group-content"
        >
          <b-form-input
            id="input-cost"
            v-model="form.courseCost"
            type="text"
            placeholder="Enter Cost"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-5"
          label="Requires Instructor:"
          v-slot="{ ariaDescribedby }"
          class="form-group-content"
        >
          <b-form-checkbox v-model="form.requiresInstructor">Requires Instructor</b-form-checkbox>
        </b-form-group>

        <div class="buttons-container">
          <b-button @click="onReturn" variant="info">Return</b-button>
          <b-button type="reset" variant="info">Reset</b-button>
          <b-button type="submit" variant="primary">Next</b-button>
        </div>
        <p v-if="error" style="color: red">{{ error }}</p>
        <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
      </b-form>
      <!-- <b-card class="mt-3" header="Form Data Result"> 
        <pre class="m-0">{{ form }}</pre>
      </b-card> -->
    </b-card>
  </div>
</template>

<script src="../javascript/CreateCourseAdmin.js"></script>

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
