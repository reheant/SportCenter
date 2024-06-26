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
                    {key: 'accountEmail', sortable:true},
                    {key: 'action', sortable:true},
                  ],
        items: [],
        selectMode: 'multi',
        selected: [],
        currentPage: 1,
        perPage: 10,
        sortDesc: false,
        sortBy: 'lastName',
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
    this.fetchInstructors();
  },


  methods: {

    fetchInstructors() {
      AXIOS.get('/instructors')
      .then(response => {
        this.items = response.data.map(instructor => ({
          accountEmail: instructor.accountEmail,
          firstName: instructor.firstName,
          lastName: instructor.lastName,
          action: instructor.accountEmail
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
     onLogout() {
      logout();
     this.$router.push("/login");
    },
    demoteInstructor() {

      this.selected.forEach(instructor => { const email = instructor.accountEmail;

        AXIOS.post(`/demote/${encodeURIComponent(email)}`, null, {
        })
          .then(response => {
            this.fetchInstructors();
            this.successMessage = `Instructor demoted successfully.`;
            this.errorMessage = '';
          })
          .catch(error => {
            this.errorMessage = `An issue has occured during demotion.`;
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

