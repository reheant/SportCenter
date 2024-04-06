import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

function OwnerDto(
  firstName,
  lastName,
  email,
  password,

) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.email = email;
  this.password = password;

}

export default {
    name: 'createOwner',
    data() {
      return {
        form: {
          email: '',
          firstName: '',
          lastName: '',
          password: '',
        },
        show: true,
        error: '' 
      };
    },
    methods: {
      createOwner() {
        if (this.form.firstName === "") {
          this.error = "first name required";
        } else if (this.form.lastName === "") {
          this.error = "last name required";
        } else if (this.form.email === "") {
          this.error = "email required";
        } else if (this.form.password === "") {
          this.error = "password required";
        } else {
            const formData = new URLSearchParams();
            formData.append('lastName', this.form.lastName);
            formData.append('email', this.form.email);
            formData.append('password', this.form.password);
            

        AXIOS.post(`/owner/${this.form.firstName}`, formData)
          .then((response) => {
              console.log(response.data);
              this.error = '';
          })
          .catch((e) => {
            const errorMsg = e.response && e.response.data ? e.response.data : "something went wrong";
            console.error(errorMsg);
            this.error = errorMsg; 
          });
      }
    },
    onSubmit() {
      this.createOwner();
    },
    onReset() {
      this.form = {
        email: "",
        firstName: "",
        lastName: "",
        password: "",
        checked: false,
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
        this.$router.push("ViewOwners");
      },
  },
};
