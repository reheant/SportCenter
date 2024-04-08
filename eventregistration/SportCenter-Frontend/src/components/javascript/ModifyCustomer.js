import axios from "axios";
import config from "../../../config";
import { logout } from '../../helper/login';


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
        accountEmail: "",
        firstName: "",
        lastName: "",
        password: "",
        wantsEmailConfirmation: false,
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },

  created() {
    AXIOS.get(`/customer/${this.$route.params.email}`)
      .then((r) => {
        this.form.accountEmail= r.data.accountEmail,
        this.form.firstName= r.data.firstName,
        this.form.lastName= r.data.lastName,
        this.form.password= r.data.password,
        this.form.wantsEmailConfirmation = r.data.wantsEmailConfirmation
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
      AXIOS.post(`customer`, this.form)
        .then((response) => {
          console.log(response.data);
          this.successMessage = "Customer updated successfully";
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
     onLogout() {
      logout();
     this.$router.push("/login");
    },

    onReset() {
      this.form = {
        accountEmail: "",
        firstName: "",
        lastName: "",
        password: "",
        wantsEmailConfirmation: false,
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      this.$router.back();
    },
  },
};
