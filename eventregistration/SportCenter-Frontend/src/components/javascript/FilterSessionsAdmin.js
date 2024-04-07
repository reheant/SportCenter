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
  name: "filterSessionsAdmin",
  data() {
    return {
      form: {
        ids: "",
        name: "",
        date: "",
        duration: "",
        instructor: ""
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },
  methods: {
    filterSession() {
      const params = new URLSearchParams();
      if (this.form.ids) params.append("ids", this.form.ids);
      if (this.form.name) params.append("name", this.form.name);
      if (this.form.date) params.append("date", this.form.date);
      if (this.form.duration) params.append("duration", this.form.duration);
      if (this.form.instructor) params.append("instructor", this.form.instructor);

      AXIOS.get(`/sessions/filter?${params.toString()}`)
        .then((response) => {
          console.log(response.data);
          this.successMessage = "";
          this.error = "";
          setTimeout(() => {
            this.$router.push({ name: 'DisplaySessionsAdmin', params: { filteredData: response.data }});
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
      this.filterSession();
    },
    onReset() {
      this.form = {
        ids: "",
        name: "",
        date: "",
        duration: "",
        instructor: ""
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      this.$router.push("DisplaySessions");
    },
  }
}
