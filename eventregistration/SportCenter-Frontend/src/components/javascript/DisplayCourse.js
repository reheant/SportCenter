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

 
    toggleRowSelection(item) {
      console.log('test')
      this.selected.push(item);
      console.log("Selected Courses:", this.selected);
    },

    onRowSelected(items) {
      this.selected = items
      console.log(this.selected);
    },

    selectAllRows() {
      console.log('selectedCourseNames',this.selectedCourseNames);
      console.log('selected', this.selected);
      console.log('items',this.items);

      this.clearSelected()
      this.$refs.selectableTable.selectAllRows();
      
      // Add course names to the selected array
      this.selected = [...this.selected, ...this.items];
      
      console.log('selectedCourseNames',this.selectedCourseNames);
      console.log('selected', this.selected);
      console.log('items',this.items);
    },

    clearSelected() {
      this.$refs.selectableTable.clearSelected();
      this.selected = [];
      console.log(this.selectedCourseNames);
    },

    // Approve selected rows
    approveCourse() {
      const email = 'admin@mail.com'; // Assuming the email is constant for approval action
      console.log('calling approve')
      console.log(this.selected);
      this.selected.forEach(course => { const name = course.course_name; 
    
        AXIOS.post(`/approve/${encodeURIComponent(name)}`, null, {
          params: { email: email }
        })
          .then(response => {
            this.fetchCourses();
            console.log(`Course ${name} approved successfully.`);
          })
          .catch(error => {
            // Handle error if needed
            console.error(`Error approving course ${name}:`, error);
          });
      });
    },
    disapproveCourse() {
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
  }
}
