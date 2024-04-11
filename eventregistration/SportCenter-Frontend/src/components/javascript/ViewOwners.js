import axios from 'axios'
import config from '../../../config'
import { logout } from '../../helper/login';


const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});


export default {
    name: 'viewAccounts',
    data() {
        return {
        
        fields: [ {key: 'selected',sortable:false},
                    {key: 'firstName', sortable:true},
                    {key: 'lastName', sortable:true}, 
                    {key: 'accountEmail', sortable:true},],
        items: [],
        selectMode: 'multi', // 
        selected: [],
        currentPage: 1, // initial current page
        perPage: 10, // initial items per page
        sortDesc: false,
        sortBy: 'lastName',
        successMessage: '',
        errorMessage: '',
        };
    
  },
  computed: {
    selectedCourseNames() {
      return this.selected.map(item => item.lastName);
    },
    totalRows() {
      return this.items.length;
    }
  },
  
  created() {
    this.fetchOwners(); // Fetch customers when the component is created
  },
  
  
  methods: {

    fetchOwners() {
      AXIOS.get('/owners')
      .then(response => {
        this.items = response.data.map(owner => ({
          accountEmail: owner.accountEmail,
          firstName: owner.firstName,
          lastName: owner.lastName
        }));
        
      })
      .catch(error => {
        console.error('Error fetching owners:', error);
      });
    },


    onRowSelected(items) {
      this.selected = items
      console.log(this.selected);
    },

    selectAllRows() {

      this.clearSelected()
      this.$refs.selectableTable.selectAllRows();
  
    },

    clearSelected() {
      this.$refs.selectableTable.clearSelected();
      this.selected = [];
    },

    onPageChange(page) {
      console.log("Current Page:", page);
    },
     onLogout() {
      logout();
     this.$router.push("/login");
    },

  },
  
  watch: {

    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};

