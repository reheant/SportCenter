import axios from 'axios'
import config from '../../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  
})

export default {
    name: 'login',
    data() {
      return {
        form: {
          email: '',
          password: '',
          userType: "Customer",
        },
        error: '' ,
        show: true
      };
    },
    methods: {
      login() {
        console.log(this.form);
        AXIOS.post("/login", this.form)
          .then((r) => {
            console.log(r.data);
            localStorage.setItem("user_id", r.data.id);
            localStorage.setItem("account_email", this.form.email);
            localStorage.setItem("user_role", r.data.role);
            this.$router.back();
          })
          .catch((e) => {
            const errorMsg = e.response && e.response.data.error ? e.response.data.error : "Something went wrong";
            console.error(errorMsg);
            this.error = errorMsg;
          })
      },
      onSubmit() {
        this.login();
      },
      onReset() {
        this.form = {
          email: "",
          password: "",
          role: ""
        };
        this.error = '';
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        })
      }
    },
  }