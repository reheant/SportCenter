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
    // Select the third row
  },
};