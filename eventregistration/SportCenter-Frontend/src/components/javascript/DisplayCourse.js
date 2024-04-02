import axios from 'axios';
import config from '../../../config';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
});

export default {
  data() {
    return {
      fields: ['selected', 'course_name', 'instructor', 'course_status'],
      items: [
        { selected: true,  course_name: 'Yoga', instructor: 'Macdonald', course_status:'Pending'},
        { selected: true,  course_name: 'Box', instructor: 'Macdonald', course_status:'Approved'},
        { selected: true,  course_name: 'Tennis', instructor: 'Macdonald', course_status:'Pending'},
        { selected: true,  course_name: 'Polo', instructor: 'Macdonald', course_status:'Rejected'},
      ],
      selectMode: 'multi',
      selected: []
    };
  },
  methods: {
    // Update selected rows
    onRowSelected(items) {
      this.selected = items;
    },
    // Select all rows
    selectAllRows() {
      this.$refs.selectableTable.selectAllRows();
    },
    // Clear selected rows
    clearSelected() {
      this.$refs.selectableTable.clearSelected();
    },
    // Approve selected rows
 
    approveCourse() {
      const selectedItems = this.selected;
        selectedItems.forEach(item => {
        const name = item.course_name; // Assuming the course name is stored in the 'course_name' property
        //FIXME:
        const email = 'admin@mail.com';
    
        axios.post(`/approve/${name}`, { params: { email } })
          .then(response => {
            // Handle successful response if needed
            console.log(response.data); // Log the response data
          })
          .catch(error => {
            // Handle error if needed
            console.error('Error approving course:', error);
          });
      });
    },
    disapproveCourse() {
      const selectedItems = this.selected;
      selectedItems.forEach(item => {
        const name = item.course_name; // Assuming the course name is stored in the 'course_name' property
        const email = 'admin@mail.com'; // Set the email parameter for disapproval action
  
        axios.post(`/disapprove/${name}`, { params: { email } })
          .then(response => {
            // Handle successful response if needed
            console.log(response.data); // Log the response data
          })
          .catch(error => {
            // Handle error if needed
            console.error('Error disapproving course:', error);
          });
      });
    }
  }
}