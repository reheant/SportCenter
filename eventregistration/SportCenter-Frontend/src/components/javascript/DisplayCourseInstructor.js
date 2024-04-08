import axios from "axios";
import config from "../../../config";
import { logout } from '../../helper/login';


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
        { key: "course_status", sortable: true },
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
  },
  filteredItems() {
    if (!this.selectedStatus) {
      return this.items;
    }
    return this.items.filter(
      (item) => item.course_status === this.selectedStatus
    );
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
      console.log(selected)
    },

    clearSelected() {
      this.$refs.selectableTable.clearSelected();
      this.selected = [];
    },
     onLogout() {
      logout();
     this.$router.push("/login");
    },

    onPageChange(page) {
      console.log("Current Page:", page);
    },
  },
  watch: {
    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};
