import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  props: ['filteredData'],
  data() {
    return {
      fields: [
        { key: "selected", sortable: false },
        { key: "course_name", sortable: true },
        { key: "course_description", sortable: false },
        { key: "course_cost", sortable: true },
        { key: "course_duration", sortable: true },
        { key: "requires_instructor", sortable: true },
      ],
      items: [],
      selectMode: "multi",
      selected: [],
      currentPage: 1, // initial current page
      perPage: 10, // initial items per page
      sortDesc: false,
      sortBy: "course_name",
    };
  },
  computed: {
    selectedCourseNames() {
      return this.selected.map((item) => item.course_name);
    },
    totalRows() {
      return this.items.length;
    },
    filteredItems() {
      if (!this.selectedStatus) {
        return this.items;
      }
      return this.items.filter(
        (item) => item.course_status === this.selectedStatus
      );
    }
  },
  created() {
    if (this.filteredData) {
      this.fetchFilteredCourses();
    } else {
      this.fetchCourses();
    }
  },
  methods: {
    fetchCourses() {
      // Make an HTTP GET request to fetch all courses
      AXIOS.get("/courses")
        .then((response) => {
          // Update items array with the fetched courses
          this.items = response.data.map((course) => ({
            course_name: course.name,
            requires_instructor: course.requiresInstructor,
            course_cost: course.cost,
            course_description: course.description,
            course_duration: course.defaultDuration,
            course_status: course.courseStatus,
            course_id: course.id,

            // Add other fields as needed
          }));
        })
        .catch((error) => {
          console.error("Error fetching courses:", error);
        });
    },
    fetchFilteredCourses() {
      AXIOS.get("/courses")
        .then((response) => {
          this.items = this.filteredData.map((course) => ({
            course_name: course.name,
            requires_instructor: course.requiresInstructor,
            course_cost: course.cost,
            course_description: course.description,
            course_duration: course.defaultDuration,
            course_status: course.courseStatus,
            course_id: course.id,
          }));
        })
        .catch((error) => {
          console.error("Error fetching courses:", error);
        });
    },
    onRowSelected(items) {
      this.selected = items;
      console.log(this.selected);
    },

    selectAllRows() {
      this.clearSelected();
      this.$refs.selectableTable.selectAllRows();
    },

    clearSelected() {
      this.$refs.selectableTable.clearSelected();
      this.selected = [];
    },

    onPageChange(page) {
      console.log("Current Page:", page);
      // You can perform any necessary actions here when the page changes
    },

    // Approve selected rows
    approveCourse() {
      const email = "admin@mail.com"; // Assuming the email is constant for approval action
      console.log("calling approve");
      console.log(this.selected);
      this.selected.forEach((course) => {
        const name = course.course_name;

        AXIOS.post(`/approve/${encodeURIComponent(name)}`, null, {
          params: { email: email },
        })
          .then((response) => {
            this.fetchCourses();
            console.log(`Course ${name} approved successfully.`);
          })
          .catch((error) => {
            // Handle error if needed
            console.error(`Error approving course ${name}:`, error);
          });
      });
    },
    disapproveCourse() {
      const email = "admin@mail.com"; // Assuming the email is constant for approval action
      console.log("calling disapprove");
      console.log(this.selected);
      this.selected.forEach((course) => {
        const name = course.course_name;

        AXIOS.post(`/disapprove/${encodeURIComponent(name)}`, null, {
          params: { email: email },
        })
          .then((response) => {
            // Handle successful response if needed
            this.fetchCourses();
            console.log(`Course ${name} disapproved successfully.`);
          })
          .catch((error) => {
            // Handle error if needed
            console.error(`Error disapproving course ${name}:`, error);
          });
      });
    },
    deleteCourse() {
        //TODO: not implement
      },
  },
  watch: {
    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};
