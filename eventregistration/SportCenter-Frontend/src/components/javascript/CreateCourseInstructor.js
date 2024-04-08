import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

function CourseDto(
  courseName,
  courseDescription,
  requiresInstructor,
  courseDuration,
  courseCost
) {
  this.courseName = courseName;
  this.courseDescription = courseDescription;
  this.requiresInstructor = requiresInstructor;
  this.courseDuration = parseFloat(courseDuration);
  this.courseCost = parseFloat(courseCost);
}

export default {
  name: "createCourseInstructor",
  data() {
    return {
      form: {
        courseName: "",
        courseDescription: "",
        courseDuration: "",
        courseCost: "",
        requiresInstructor: false,
      },
      show: true,
      error: "",
      successMessage: "",
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
       
        const formData = new URLSearchParams();
 
        formData.append("description", this.form.courseDescription);
        formData.append("requiresInstructor", this.form.requiresInstructor);
        formData.append("defaultDuration", this.form.courseDuration);
        formData.append("cost", this.form.courseCost);

        AXIOS.post(`/course/${this.form.courseName}`, formData)
          .then((response) => {
            console.log(response.data);
            this.successMessage = "Course created successfully";
            this.error = "";
            setTimeout(() => {
              this.$router.push("DisplayCourse");
            }, 300);
          })
          .catch((e) => {
            const errorMsg =
              e.response && e.response.data
                ? e.response.data
                : "something went wrong";
            console.error(errorMsg);
            this.error = errorMsg;
          });
      }
    },
    logout() {
        logout();
        this.$router.push("login");
      },
  
    resetError() {
      this.error = "";
      this.successMessage = "";
    },
    resetSucces() {
      this.successMessage = "";
    },
    onSubmit() {
      this.createCourse();
    },
    onReset() {
      this.form = {
        courseName: "",
        courseDescription: "",
        courseDuration: "",
        courseCost: "",

        requiresInstructor: false,
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      this.$router.push("DisplayCourse");
    },
  },
};
