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
  data() {
    return {
      form: {
        courseId: -1,
        courseName: "",
        courseDescription: "",
        courseDuration: "",
        courseCost: "",
        courseStatus: "",
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },

  created() {
    AXIOS.get(`/course/${this.$route.params.id}`)
      .then((r) => {
        this.form.id = r.data.id;
        this.form.name = r.data.name;
        this.form.description = r.data.description;
        this.form.defaultDuration = r.data.defaultDuration;
        this.form.cost = r.data.cost;
        this.form.courseStatus = r.data.courseStatus;
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

  methods: {
    modifyCourse() {
      console.log(this.form);
      AXIOS.post(`sportscenter/modify/courses`, this.form)
        .then((response) => {
          console.log(response.data);
          this.successMessage = "Course updated successfully";
          this.error = "";
          setTimeout(() => {
            this.$router.back();
          }, 500);
        })
        .catch((e) => {
          console.log(e);
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
      this.modifyCourse();
    },
    logout() {
      logout();
      this.$router.push("login");
    },

    onReset() {
      this.form = {
        courseId: -1,
        courseName: "",
        courseDescription: "",
        courseDuration: "",
        courseCost: "",
        courseStatus: "",
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      setTimeout(() => {
        this.$router.back();
      }, 500);
    }
  },
};
