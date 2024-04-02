import axios from 'axios'
import config from '../../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  
})

function CustomerDto(firstName, lastName, email, password, wantsEmailConfirmation ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.wantsEmailConfirmation = wantsEmailConfirmation;
}

export default {
    name: 'addPaymentInfo',
    data() {
      return {
        form: {
          email: '',
          firstName: '',
          lastName: '',
          password: '',
          checked: false 
        },
        show: true,
        error: '' 
      };
    },
    methods: {
        addPaymentInfo() {
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
            formData.append('wantsEmailConfirmation', this.form.checked);

            AXIOS.post(`/customer/${this.form.firstName}`, formData)
          .then((response) => {
              console.log(response.data);
              this.error = '';
          })
          .catch((e) => {
            const errorMsg = e.response ? e.response.data.message : "An error occurred";
            console.log(errorMsg);
            this.error = errorMsg;
          });
        }
      },
      onSubmit() {
        this.createCustomer(); 
      },
      onReset() {
        this.form = {
          email: '',
          firstName: '',
          lastName: '',
          password: '',
          checked: false
        };
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        });
      }
    },
  }