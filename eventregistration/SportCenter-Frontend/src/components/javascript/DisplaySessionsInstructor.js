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
        { key: "start_time", sortable: true },
        { key: "end_time", sortable: true },
        { key: "course_name", sortable: true },
        { key: "location", sortable: true },
        { key: "assigned", sortable: true },
      ],
      items: [],
      selectMode: "single",
      selected: [],
      assigned: [],
      currentPage: 1, // initial current page
      perPage: 10, // initial items per page
      sortDesc: false,
      sortBy: "session_name",
    };
  },
  computed: {
    selectedSessionNames() {
      return this.selected.map((item) => item.session_name);
    },
    totalRows() {
      return this.items.length;
    },
  },

  created() {
    if (this.filteredData) {
      this.fetchFilteredSessions();
    } else {
      this.fetchSessions();
    }
  },

  methods: {
    formatDateTime(dateTimeString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
      const date = new Date(dateTimeString);
      return new Intl.DateTimeFormat('en-US', options).format(date);
    },
    fetchSessions() {
      // Make an HTTP GET request to fetch all sessions
      const instructorEmail = localStorage.getItem("account_email");
      AXIOS.get("/sessions")
        .then(async (response) => {
          // Update items array with the fetched sessions
          this.items = await Promise.all(response.data.map(async (session) => ({
            id: session.id,
            start_time: this.formatDateTime(session.startTime),
            end_time: this.formatDateTime(session.endTime),
            course_name: session.courseName,
            location: session.locationName,
            assigned: await this.getIsAssigned(session.id, instructorEmail),
          })));
        })
        .catch((error) => {
          console.error("Error fetching sessions:", error);
        });
    },

    async getIsAssigned(sessionId, InstructorEmail) {
        try {
          const params = {
            email: InstructorEmail,
            sessionId: sessionId
          };
          const urlWithParams = `/assignment/?${new URLSearchParams(params).toString()}`;
      
          const response = await AXIOS.get(urlWithParams);
          return "X";
        } catch (error) { // a sketchy way to check if registered
          return "";
        }
      },

    fetchFilteredSessions() {
          // Make an HTTP GET request to fetch all sessions
          AXIOS.get("/sessions")
            .then((response) => {
              // Update items array with the fetched sessions
              this.items = this.filteredData.map((session) => ({
                id: session.id,
                start_time: this.formatDateTime(session.startTime),
                end_time: this.formatDateTime(session.endTime),
                course_name: session.courseName,
                location: session.locationName,
              }));
            })
            .catch((error) => {
              console.error("Error fetching sessions:", error);
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
     onLogout() {
      logout();
     this.$router.push("/login");
    },


  },
  deleteSession() {
    //TODO: not implement
  },
  watch: {
    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};
