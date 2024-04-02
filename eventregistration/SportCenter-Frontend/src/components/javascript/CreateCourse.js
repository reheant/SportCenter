import axios from 'axios';
import config from '../../../config';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

function CourseDto(courseName, courseDescription, requiresInstructor, courseDuration, courseCost) {
  this.courseName = courseName;
  this.courseDescription = courseDescription;
  this.requiresInstructor = requiresInstructor;
  this.courseDuration = parseFloat(courseDuration);
  this.courseCost = parseFloat(courseCost);
}

export default {
  name: 'createCourse',
  data() {
    return {
      form: {
        courseName: '',
        courseDescription: '',
        //courseStatus: '', // Assuming you need this field, if not, you can remove it
        courseDuration: '',
        courseCost: '',
        requiresInstructor: false,
        
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
        //formData.append('name', this.form.courseName);
        formData.append('courseDescription', this.form.courseDescription);
        formData.append('requiresInstructor', this.form.courseRequiresInstructor);
        formData.append('courseDuration', this.form.courseDuration);
        formData.append('courseCost', this.form.courseCost);

        AXIOS.post(`/course/${this.form.courseName}`, formData)
        .then(response => {
        console.log(response.data);
        // Reset form and error message after successful creation
        this.error = '';
        this.resetForm();
  })
  .catch((e) => {
    const errorMsg = e.response ? e.response.data.message : "An error occurred";
    console.log(errorMsg);
    this.error = errorMsg;
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
        courseDuration: '',
        courseCost: '',
        //courseStatus: '',
        requiresInstructor: false,
        
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
