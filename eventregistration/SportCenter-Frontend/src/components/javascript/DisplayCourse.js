import axios from 'axios'
import config from '../../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});


export default {
  data() {
    return {
      fields: ['selected', 'course_name', 'instructor', 'course_status'],
      items: [],
      selectMode: 'multi',
      selected: []
    };
  },
  computed: {
    selectedCourseNames() {
      return this.selected.map(item => item.course_name);
    }
  },
  created() {
    this.fetchCourses(); // Fetch courses when the component is created
  },
  methods: {

    fetchCourses() {
      // Make an HTTP GET request to fetch all courses
      AXIOS.get('/courses')
      .then(response => {
        // Update items array with the fetched courses
        this.items = response.data.map(course => ({
          course_name: course.name,
          instructor: course.instructor,
          course_status: course.courseStatus,
          // Add other fields as needed
        }));
      })
      .catch(error => {
        console.error('Error fetching courses:', error);
      });
    },

    toggleRowSelection(items) {
      // Toggle row selection
      this.$refs.selectableTable.toggleRowSelection(items);
      // Update selected items
      this.selected = this.$refs.selectableTable.selected;
      console.log(this.selectedCourseNames);
      console.log(this.items);
    },

    onRowSelected(items) {
      
      this.selected = items;
      console.log(this.items);
      console.log(this.selectedCourseNames);
    },

    selectAllRows() {
      this.$refs.selectableTable.selectAllRows();
      
      // Add course names to the selected array
      this.selected = [...this.selected, ...this.items];
      
      console.log(this.selectedCourseNames);
      console.log(this.selected);
      console.log(this.items);
    },

    clearSelected() {
      this.$refs.selectableTable.clearSelected();
      console.log(this.selectedCourseNames);
    },

    // Approve selected rows
    approveCourse() {
      const email = 'admin@mail.com'; // Assuming the email is constant for approval action
    
      this.items.forEach(course => { const name = course.course_name; 
    
        AXIOS.post(`/approve/${encodeURIComponent(name)}`, { email: email })
          .then(response => {
            // Handle successful response if needed
            console.log(`Course ${name} approved successfully.`);
          })
          .catch(error => {
            // Handle error if needed
            console.error(`Error approving course ${name}:`, error);
          });
      });
    },
    disapproveCourse() {
      const selectedCourseNames = this.selectedCourseNames;
      selectedCourseNames.forEach(name => {
        // Assuming the course name is stored in the 'course_name' property
        const email = 'admin@mail.com'; // Set the email parameter for disapproval action

        axios.post(`/disapprove/:name`, email)
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
