import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "filterCoursesInstructor",
  data() {
    return {
      form: {
        ids: "",
        keyword: "",
        courseStatus: "",
        requiresInstructor: false,
        defaultDuration: "",
        cost: "",
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },
  methods: {
    filterCourse() {
      const params = new URLSearchParams();
      if (this.form.ids) params.append("ids", this.form.ids);
      if (this.form.keyword) params.append("keyword", this.form.keyword);
      if (this.form.courseStatus) params.append("courseStatus", this.form.courseStatus);
      if (this.form.requiresInstructor) params.append("requiresInstructor", this.form.requiresInstructor);
      if (this.form.defaultDuration) params.append("defaultDuration", this.form.defaultDuration);
      if (this.form.cost) params.append("cost", this.form.cost);

      AXIOS.get(`/courses/filter?${params.toString()}`)
        .then((response) => {
          console.log(response.data);
          this.successMessage = "";
          this.error = "";
          setTimeout(() => {
            this.$router.push({ name: 'DisplayCourseInstructor', params: { filteredData: response.data }});
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
      },
    // Reset error message
    resetError() {
      this.error = "";
      this.successMessage = "";
    },
    resetSucces() {
      this.successMessage = "";
    },
    onSubmit() {
      this.filterCourse();
    },
    logout() {
      logout();
      this.$router.push("login");
    },
    onReset() {
      this.form = {
        ids: "",
        keyword: "",
        courseStatus: "",
        requiresInstructor: false,
        defaultDuration: "",
        cost: "",
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      this.$router.push("DisplayCourse");
    },
  }
}
