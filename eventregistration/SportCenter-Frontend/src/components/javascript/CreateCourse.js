import axios from 'axios';
import config from '../../../config';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

function CourseDto(courseName, courseDescription, courseRequiresInstructor, courseDuration, courseCost) {
  this.name = courseName;
  this.description = courseDescription;
  this.requiresInstructor = courseRequiresInstructor;
  this.defaultDuration = parseFloat(courseDuration);
  this.cost = parseFloat(courseCost);
}

export default {
  name: 'createCourse',
  data() {
    return {
      form: {
        courseName: '',
        courseDescription: '',
        courseStatus: '', // Assuming you need this field, if not, you can remove it
        courseRequiresInstructor: false,
        courseDuration: '',
        courseCost: '',
      },
      show: true,
      error: '',
    };
  },
  methods: {
    createCourse() {
      if (this.form.courseName === "") {
        this.error = "Course name is required";
      } else if (this.form.courseDescription === "") {
        this.error = "Course description is required";
      } else if (this.form.courseDuration === "") {
        this.error = "Course duration is required";
      } else if (this.form.courseCost === "") {
        this.error = "Course cost is required";
      } else {
        // Create a new instance of CourseDto
        const formData = new URLSearchParams();
        formData.append('name', this.form.courseName);
        formData.append('description', this.form.courseDescription);
        formData.append('requiresInstructor', this.form.courseRequiresInstructor);
        formData.append('defaultDuration', this.form.courseDuration);
        formData.append('cost', this.form.courseCost);

        AXIOS.post('/course', formData)
        .then(response => {
        console.log("New course created:", response.data);
        // Reset form and error message after successful creation
        this.error = '';
        this.resetForm();
  })
  .catch(error => {
    this.error = 'An error occurred while creating the course';
    console.error('Error creating course:', error);
  });
      }
    },
    // Reset error message
    resetError() {
      this.error = '';
    },
    onSubmit() {
      this.createCourse();
    },
    onReset() {
      this.form = {
        courseName: '',
        courseDescription: '',
        courseStatus: '',
        courseRequiresInstructor: false,
        courseDuration: '',
        courseCost: '',
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
