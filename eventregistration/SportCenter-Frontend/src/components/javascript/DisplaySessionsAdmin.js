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
  data() {
    return {
      fields: [
        { key: "selected", sortable: false },
        { key: "start_time", sortable: true },
        { key: "end_time", sortable: false },
        { key: "course_name", sortable: true },
        { key: "location", sortable: false }
      ],
      items: [],
      selectMode: "multi",
      selected: [],
      currentPage: 1, // initial current page
      perPage: 10, // initial items per page
      sortDesc: false,
      sortBy: "start_time",
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
    this.fetchSessions(); // Fetch sessions when the component is created
  },

  methods: {
    fetchSessions() {
      // Make an HTTP GET request to fetch all sessions
      AXIOS.get("/sessions")
        .then((response) => {
          // Update items array with the fetched sessions
          this.items = response.data.map((session) => ({
            id: session.id,
            start_time: session.startTime,
            end_time: session.endTime,
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


  },
  deleteSession() {
    //TODO: not implement
  },
  filterSession() {
    // TODO: not implement
  },
  watch: {
    currentPage(newValue) {
      this.onPageChange(newValue);
    },
  },
};
