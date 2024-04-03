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
        currentPage: 1, // initial current page
        perPage: 10, // initial items per page
        sortDesc: false,
        sortBy: 'course_name',
        
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
    this.fetchInstructors(); // Fetch customers when the component is created
  },
  
  
  methods: {

    fetchInstructors() {
      AXIOS.get('/instructors')
      .then(response => {
        this.items = response.data.map(instructor => ({
          accountEmail: instructor.accountEmail,
          firstName: instructor.firstName,
          lastName: instructor.lastName
        }));
        
      })
      .catch(error => {
        console.error('Error fetching instructors:', error);
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
            console.log(`Customer ${customer.firstName} promoted successfully.`);
          })
          .catch(error => {
            // Handle error if needed
            console.error(`Error promoting customer ${customer.firstName}:`, error);
          });
      });
    },
    deleteCustomer() {
      const email = 'admin@mail.com'; // Assuming the email is constant for approval action
      console.log('calling disapprove')
      console.log(this.selected);
      this.selected.forEach(course => { const name = course.course_name; 
    
        AXIOS.post(`/disapprove/${encodeURIComponent(name)}`, null, {
          params: { email: email }
        })
          .then(response => {
            // Handle successful response if needed
            this.fetchCourses();
            console.log(`Course ${name} disapproved successfully.`);
          })
          .catch(error => {
            // Handle error if needed
            console.error(`Error disapproving course ${name}:`, error);
          });
      });
    },
  },
  deleteCourse() {
    //TODO: not implement 
    
  },
  filterCourse(){
    // TODO: not implement
  },
  watch: {

    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};

