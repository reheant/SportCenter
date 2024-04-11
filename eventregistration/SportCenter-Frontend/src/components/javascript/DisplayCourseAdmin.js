import axios from "axios";
import config from "../../../config";
import {logout} from "../../helper/login"

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
        { key: "selected", label: "Select", sortable: false },
        { key: "course_name", label: "Name", sortable: true },
        { key: "course_description", label: "Description", sortable: false },
        { key: "course_cost", label: "Cost", sortable: true },
        { key: "course_duration", label: "Duration", sortable: true },
        { key: "requires_instructor", label: "Supervised", sortable: true },
        { key: "course_status", label: "Status", sortable: true },
        { key: "action", label: "Action", sortable: false },
      ],
      items: [],
      selectMode: "multi",
      selected: [],
      currentPage: 1,
      perPage: 10,
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

      AXIOS.get("/courses")
        .then((response) => {

          this.items = response.data.map((course) => ({
            id: course.id,
            course_name: course.name,
            requires_instructor: course.requiresInstructor,
            course_cost: course.cost,
            course_description: course.description,
            course_duration: course.defaultDuration,
            course_status: course.courseStatus,
            course_id: course.id,
            action: course.id,
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

     onLogout() {
      logout();
     this.$router.push("/login");
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

    },

    deleteCourse() {
        this.selected.forEach((course) => {
            const courseId = course.id;
            // https://developer.chrome.com/blog/urlsearchparams/
            const urlWithParams = `/courses/${courseId}`;

            AXIOS.delete(urlWithParams)
                .then((response) => {
                  this.fetchCourses();
                  this.successMessage = `Successfully deleted course with id ${courseId}.`;
                  console.log(this.successMessage);
                })
                .catch((error) => {
                  // Handle error if needed
                  const errorMsg = error.response && error.response.data ? error.response.data : "Something went wrong";
                  this.successMessage = '';
                  this.error = errorMsg;
                  console.error(`Error deleting course with id ${courseId}:`, error);
                });
        });
    },

    approveCourse() {
      console.log("calling approve");
      this.selected.forEach((course) => {
        const name = course.course_name;

        AXIOS.post(`/approve/${encodeURIComponent(name)}`)
          .then((response) => {
            this.fetchCourses();
            console.log(`Course ${name} approved successfully.`);
          })
          .catch((error) => {

            console.error(`Error approving course ${name}:`, error);
          });
      });
    },
    disapproveCourse() {
      console.log("calling disapprove");
      console.log(this.selected);
      this.selected.forEach((course) => {
        const name = course.course_name;

        AXIOS.post(`/disapprove/${encodeURIComponent(name)}`)
          .then((response) => {

            this.fetchCourses();
            console.log(`Course ${name} disapproved successfully.`);
          })
          .catch((error) => {

            console.error(`Error disapproving course ${name}:`, error);
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
