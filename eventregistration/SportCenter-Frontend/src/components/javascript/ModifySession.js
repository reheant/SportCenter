import axios from "axios";
import config from "../../../config";

const AXIOS = axios.create({
  baseURL: `http://${config.dev.backendHost}:${config.dev.backendPort}`,
  headers: { "Access-Control-Allow-Origin": `http://${config.dev.host}:${config.dev.port}` },
});

export default {
  name: "modifySession",
  data() {
    return {
      form: {
        selectedSession: null,
        startTime: "",
        endTime: "",
        selectedCourse: null,
        selectedLocation: null,
      },
      sessions: [], // List of sessions with their IDs
      courses: [], // List of courses with their IDs
      locations: [], // List of locations with their IDs
      error: "",
    };
  },
  created() {
    this.fetchInitialData();
  },
  methods: {
    fetchInitialData() {
      // Fetch sessions, courses, and locations from the backend
      // For example:
      AXIOS.get('/sessions').then(response => {
        this.sessions = response.data; // Assume each session has { name, id } properties
      });
      AXIOS.get('/courses').then(response => {
        this.courses = response.data; // Assume each course has { name, id } properties
      });
      AXIOS.get('/locations').then(response => {
        this.locations = response.data; // Assume each location has { name, id } properties
      });
    },
    modifySessionDetails() {
      if (this.form.selectedSession && this.form.startTime && this.form.endTime && this.form.selectedCourse && this.form.selectedLocation) {
        const sessionId = this.form.selectedSession.id; // Get the selected session ID
        const courseId = this.form.selectedCourse.id; // Get the selected course ID
        const locationId = this.form.selectedLocation.id; // Get the selected location ID

        const updatePromises = [];

        updatePromises.push(AXIOS.post(`/schedule/modify/sessions/${sessionId}/time`, null, {
          params: {
            startTime: this.form.startTime,
            endTime: this.form.endTime,
          }
        }));

        updatePromises.push(AXIOS.post(`/schedule/modify/sessions/${sessionId}/course`, null, {
          params: {
            courseId: courseId,
          }
        }));

        updatePromises.push(AXIOS.post(`/schedule/modify/sessions/${sessionId}/location`, null, {
          params: {
            locationId: locationId,
          }
        }));

        Promise.all(updatePromises).then((responses) => {
          console.log("Session details updated", responses);
          this.error = "";
        }).catch((e) => {
          this.handleError(e);
        });
      } else {
        this.error = "All fields are required to modify the session.";
      }
    },
    
  },
};
