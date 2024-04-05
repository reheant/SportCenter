import axios from 'axios'
import config from '../../../config'

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
                    {key: 'lastName', sortable:false}, 
                    {key: 'accountEmail', sortable:true},],
        items: [],
        selectMode: 'multi',
        selected: [],
        currentPage: 1, 
        perPage: 10, 
        sortDesc: false,
        sortBy: 'firstName',
        successMessage: '',
        errorMessage: '',
        };
    
  },
  computed: {
    selectedCourseNames() {
      return this.selected.map(item => item.course_name);
    },
    totalRows() {
      return this.items.length;
    }
  },
  filteredItems() {
      if (!this.selectedStatus) {
        return this.items; 
      }
      return this.items.filter(item => item.course_status === this.selectedStatus);
    },
    
  created() {
    this.fetchCustomers(); 
  },
  
  
  methods: {

    fetchCustomers() {
      AXIOS.get('/customers')
      .then(response => {
        this.items = response.data.map(customer => ({
          accountEmail: customer.accountEmail,
          firstName: customer.firstName,
          lastName: customer.lastName
        }));
        
      })
      .catch(error => {
        console.error('Error fetching customers:', error);
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
    
    promoteCustomer() {

      this.selected.forEach(customer => { const email = customer.accountEmail; 
    
        AXIOS.post(`/promote/${encodeURIComponent(email)}`, null, {
        })
          .then(response => {
            this.fetchCustomers();
            this.successMessage = `Customer promoted successfully.`;
            this.errorMessage = ''; 
          })
          .catch(error => {
            this.errorMessage = `Chosen customer is aleady an Instructor.`;
            this.successMessage = '';
          });
      });
    },
    deleteCustomer() {
      this.selected.forEach(customer => { const email = customer.accountEmail; 
    
        AXIOS.delete(`/delete/${encodeURIComponent(email)}`, null, {
        })
          .then(response => {
            this.fetchCustomers();
            this.successMessage = `Customer ${customer.firstName} deleted successfully.`;
            this.errorMessage = ''; 
          })
          .catch(error => {
            this.errorMessage = `Error deleting customer ${customer.firstName}.`;
            this.successMessage = ''; 
          });
      });
    },
  },

  watch: {

    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};

