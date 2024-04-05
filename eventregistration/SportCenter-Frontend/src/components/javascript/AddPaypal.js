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
    name: 'addPaypal',
    data() {
      return {
        form: {
          accountName: '',
          customerEmail: '',
          paypalEmail: '',
          password: '',
        },
        show: true,
        error: '' 
      };
    },
    methods: {
      addPaypal() {
        if (this.form.accountName === "") {
          this.error = "Account name required";
        } else if (this.form.customerEmail === "") {
          this.error = "Customer email required";
        } else if (this.form.paypalEmail === "") {
          this.error = "Paypal email required";
        } else if (this.form.password === "") {
          this.error = "password required";
        } else {
            const formData = new URLSearchParams();
            formData.append('accountName', this.form.accountName);
            formData.append('customerEmail', this.form.customerEmail);
            formData.append('paypalEmail', this.form.paypalEmail);
            formData.append('paypalPassword', this.form.password);

            AXIOS.post(`/paypal/add`, formData)
          .then((response) => {
              console.log(response.data);
              this.error = '';
              this.$router.push('/login'); //ROUTES BACK TO LOGIN
          })
          .catch((e) => {
            const errorMsg = e.response && e.response.data ? e.response.data : "something went wrong";
            console.error(errorMsg);
            this.error = errorMsg; 
          });
        }
      },
      onSubmit() {
        this.addPaypal();
      },
      onReset() {
        this.form = {
          accountName: '',
          customerEmail: '',
          paypalEmail: '',
          password: '',
        };
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        });
      }
    },
  }