import axios from 'axios'
import config from '../../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  
})

export default {
    name: 'selectPaymentType',
    data() {
        return {
        selectedForm: 'form1',
        show: true, 
        error: '', 
        };
    },
    methods: {
      onSubmit() {
        if (this.selectedForm === 'form1') {
            this.$router.push('/addPaypal');
          } else if (this.selectedForm === 'form2') {
            this.$router.push('/addCard');
          } else {
            console.error('No form selected');
          }
      },

    },
  }