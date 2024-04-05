<template>
  <div class="modify-session-container">
    <h1>Modify Session</h1>
    <b-form @submit.prevent="modifySessionDetails">

      <b-form-group label="Select Session" label-for="session-dropdown">
        <b-form-select
          id="session-dropdown"
          v-model="form.selectedSession"
          :options="sessionsOptions"
          text-field="name"
          value-field="id"
          placeholder="Choose a session..."
        ></b-form-select>
      </b-form-group>

      <b-form-group label="Course Name" label-for="course-dropdown">
        <b-form-select
          id="course-dropdown"
          v-model="form.selectedCourse"
          :options="coursesOptions"
          text-field="name"
          value-field="id"
          placeholder="Choose a course..."
        ></b-form-select>
      </b-form-group>

      <b-form-group label="Location Name" label-for="location-dropdown">
        <b-form-select
          id="location-dropdown"
          v-model="form.selectedLocation"
          :options="locationsOptions"
          text-field="name"
          value-field="id"
          placeholder="Choose a location..."
        ></b-form-select>
      </b-form-group>

      <b-form-group label="Start Time" label-for="start-time">
        <b-form-timepicker
          id="start-time"
          v-model="form.startTime"
          placeholder="Select start time"
        ></b-form-timepicker>
      </b-form-group>

      <b-form-group label="End Time" label-for="end-time">
        <b-form-timepicker
          id="end-time"
          v-model="form.endTime"
          placeholder="Select end time"
        ></b-form-timepicker>
      </b-form-group>

      <b-button type="submit" variant="success">Save Changes</b-button>
    </b-form>
  </div>
</template>

<script>
import ModifySessionJS from '../javascript/ModifySession.js'; // Adjust the path as needed

export default {
  name: "ModifySession",
  data() {
    return ModifySessionJS.data();
  },
  created() {
    ModifySessionJS.created.bind(this)();
  },
  methods: {
    ...ModifySessionJS.methods,
    sessionsOptions() {
      return this.sessions.map(s => ({ value: s, text: s.name }));
    },
    coursesOptions() {
      return this.courses.map(c => ({ value: c, text: c.name }));
    },
    locationsOptions() {
      return this.locations.map(l => ({ value: l, text: l.name }));
    }
  }
};
</script>

<style>
.modify-session-container {
  max-width: 500px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
</style>
