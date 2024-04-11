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
        form: {
            instructorEmail: ""
            },
      fields: [
        { key: "selected", sortable: false },
        { key: "start_time", sortable: true },
        { key: "end_time", sortable: true },
        { key: "course_name", sortable: true },
        { key: "location", sortable: true },
      ],
      items: [],
      selectMode: "multi",
      selected: [],
      currentPage: 1, // initial current page
      perPage: 10, // initial items per page
      sortDesc: false,
      sortBy: "start_time",
      show: true,
      error: '',
      successMessage: '',
      assigningInstructor: false,
    };
  },
  computed: {
    selectedSessionIds() {
      return this.selected.map((item) => item.id);
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
      AXIOS.get("/sessions")
      .then((response) => {
        // Update items array with the fetched sessions
        this.items = response.data.map((session) => ({
          id: session.id,
          start_time: this.formatDateTime(session.startTime),
          end_time: this.formatDateTime(session.endTime),
          course_name: session.courseName,
          location: session.locationName
        }));
        })
        .catch((error) => {
          console.error("Error fetching sessions:", error);
        });
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

    displayAssignInstructor() {
        this.assigningInstructor = !this.assigningInstructor;
        console.log(this.assigningInstructor);
    },
    deleteSession() {
        this.selected.forEach((session) => {
            const sessionId = session.id;
            // https://developer.chrome.com/blog/urlsearchparams/
            const urlWithParams = `/sessions/${sessionId}`;

            AXIOS.delete(urlWithParams)
                .then((response) => {
                  this.fetchSessions();
                  this.successMessage = `Successfully deleted for session with id ${sessionId}.`;
                  console.log(this.successMessage);
                })
                .catch((error) => {
                  // Handle error if needed
                  const errorMsg = error.response && error.response.data ? error.response.data : "Something went wrong";
                  this.successMessage = '';
                  this.error = errorMsg;
                  console.error(`Error deleting session with id ${sessionId}:`, error);
                });
        });
    },

    onPageChange(page) {
      console.log("Current Page:", page);
      // You can perform any necessary actions here when the page changes
    },

    onReset() {
        this.error = '';
        this.assigningInstructor = false;
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        })
      },

    
    onSubmit() {
        // nothing: buttons directly call the necessary actions :)
    },
     onLogout() {
      logout();
     this.$router.push("/login");
    },

    unassignInstructor(){
        const instructorAccountEmail = this.form.instructorEmail;        

        this.selected.forEach((session) => {
            const sessionId = session.id;
        
            console.log(instructorAccountEmail + " " + sessionId);

            const params = {
                instructorAccountEmail: instructorAccountEmail,
            }
            // https://developer.chrome.com/blog/urlsearchparams/
            const urlWithParams = `/schedule/modify/sessions/${sessionId}/instructor/?${new URLSearchParams(params).toString()}`;

            AXIOS.delete(urlWithParams)
                .then((response) => {
                  this.fetchSessions();
                  this.successMessage = `Instructor with email ${instructorAccountEmail} successfully unassigned to session with id ${sessionId}.`;
                  console.log(this.successMessage);
                })
                .catch((error) => {
                  const errorMsg = (error.response && error.response.data) ? error.response.data : "Something went wrong";
                  this.successMessage = '';
                  console.error(`Error unassigning instructor with email ${instructorAccountEmail} to session with id ${sessionId}:`, error);
                  this.error = errorMsg;                  
                });

        });
    },

    assignInstructor() {
        const instructorAccountEmail = this.form.instructorEmail;        

        this.selected.forEach((session) => {
            const sessionId = session.id;
        
            console.log(instructorAccountEmail + " " + sessionId);

            const urlWithParams = `/schedule/modify/sessions/${sessionId}/instructor`;

            AXIOS.post(urlWithParams, null, {
                params: { instructorAccountEmail: instructorAccountEmail,
                        },
              })
                .then((response) => {
                  this.fetchSessions();
                  this.successMessage = `Instructor with email ${instructorAccountEmail} successfully assigned to session with id ${sessionId}.`;
                  console.log(this.successMessage);
                })
                .catch((error) => {
                  const errorMsg = (error.response && error.response.data) ? error.response.data : "Something went wrong";
                  this.successMessage = '';
                  console.error(`Error assigning instructor with email ${instructorAccountEmail} to session with id ${sessionId}:`, error);
                  this.error = errorMsg;                  
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
