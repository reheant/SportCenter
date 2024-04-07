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
        id: -1,
        name: "",
        capacity: 0,
        openingTime: "",
        closingTime: "",
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },

  created() {
    AXIOS.get(`/sportscenter/location`)
      .then((r) => {
        this.form.id = r.data.id;
        this.form.name = r.data.name;
        this.form.capacity = r.data.capacity;
        this.form.openingTime = r.data.openingTime;
        this.form.closingTime = r.data.closingTime;
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

  methods: {
    modifyCourse() {
      console.log(this.form);
      AXIOS.post(`sportscenter/modify/locations`, this.form)
        .then((response) => {
          console.log(response.data);
          this.successMessage = "Location updated successfully";
          this.error = "";
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

    onReset() {
      this.form = {
        id: -1,
        name: "",
        capacity: 0,
        openingTime: "",
        closingTime: "",
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
