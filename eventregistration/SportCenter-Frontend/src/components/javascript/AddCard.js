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
    name: 'addCard',
    data() {
      return {
        form: {
          accountName: '',
          customerEmail: '',
          selectedForm: 'form1',
          expirationDate: '',
          ccv: '',
        },
        show: true,
        error: '' 
      };
    },
    methods: {
        addCard() {
        if (this.form.accountName === "") {
          this.error = "Account name required";
        } else if (this.form.customerEmail === "") {
          this.error = "Customer email required";
        } else if (this.form.expirationDate === "") {
          this.error = "Expiration date required";
        } else if (this.form.ccv === "") {
          this.error = "CCV required";
        } else {
            let paymentCardType;
            if (this.selectedForm === 'form1') {
                paymentCardType = 'DebitCard';
              } else if (this.selectedForm === 'form2') {
                paymentCardType = 'CreditCard';
              }

            const formData = new URLSearchParams();
            formData.append('accountName', this.form.accountName);
            formData.append('customerEmail', this.form.customerEmail);
            formData.append('paymentCardType', paymentCardType);
            formData.append('cardNumber', this.form.cardNumber);
            formData.append('expirationDate', this.form.expirationDate);
            formData.append('ccv', this.form.ccv);

            AXIOS.post(`/card/add`, formData)
          .then((response) => {
              console.log(response.data);
              this.error = '';
              this.$router.push('/login'); //ROUTES BACK TO LOGIN
          })
          .catch((e) => {
            const errorMsg = e.response ? e.response.data.message : "An error occurred";
            console.log(errorMsg);
            this.error = errorMsg;
          });
        }
      },
      onSubmit() {
        this.addCard();
      },
      onReset() {
        this.form = {
          accountName: '',
          customerEmail: '',
          selectedForm: 'form1',
          expirationDate: '',
          ccv: '',
        };
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        });
      }
    },
  }