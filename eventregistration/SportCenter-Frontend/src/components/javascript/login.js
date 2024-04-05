import axios from 'axios'
import config from '../../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  
})

export default {
    name: '',
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
     
     
    },
  }