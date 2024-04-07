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
            { key: "start_time", sortable: true },
            { key: "end_time", sortable: false },
            { key: "course_name", sortable: true },
            { key: "location", sortable: false },
            { key: "instructor", sortable: false }
        ],
        items: [],
        selectMode: "multi",
        selected: [],
        currentPage: 1, // initial current page
        perPage: 10, // initial items per page
        sortDesc: false,
        sortBy: "session_name",
        show: true,
        error: '',
        successMessage: '',
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
            const errorMsg = error.response && error.response.data ? error.response.data : "Something went wrong";
            this.successMessage = '';
            console.error("Error fetching sessions:", errorMsg);
            this.error = errorMsg;
            });
        },
        fetchFilteredSessions() {
            // Make an HTTP GET request to fetch all sessions
            AXIOS.get("/sessions")
                .then((response) => {
                // Update items array with the fetched sessions
                this.items = this.filteredData.map((session) => ({
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
        register() {
            const userRole = localStorage.getItem("user_role");
            if (userRole != 'Customer'){
                this.error = "You must be logged in as a customer to register for a session";
                console.log(this.error);
                return false;
            }

            const customerEmail = localStorage.getItem("account_email");

            console.log("calling register");

            console.log(this.selected);
            this.selected.forEach((session) => {
                const sessionId = session.id;

                AXIOS.post(`/registration/`, null, {
                    params: { email: customerEmail,
                            sessionId: sessionId
                            },
                })
                    .then((response) => {
                    this.fetchSessions();
                    this.successMessage = `Customer with email ${customerEmail} successfully registered for session with id ${sessionId}.`;
                    console.log(this.successMessage);
                    })
                    .catch((error) => {
                    // Handle error if needed
                    const errorMsg = (error.response && error.response.data) ? error.response.data : "Something went wrong";
                    this.successMessage = '';
                    console.error(`Error registering customer with email ${customerEmail} for session with id ${sessionId}:`, error);
                    this.error = errorMsg;
                    });

            });
        },
        unregister() {
            console.log("calling unregister");
            const customerEmail = localStorage.getItem("account_email");
            console.log(this.selected);

            let sessionId = "";
            let params = "";
            let urlWithParams = "";

            this.selected.forEach((session) => {
                sessionId = session.id;

                params = {
                    email: customerEmail,
                    sessionId: sessionId
                }
                // https://developer.chrome.com/blog/urlsearchparams/
                urlWithParams = `/unregister/?${new URLSearchParams(params).toString()}`;

                AXIOS.delete(urlWithParams)
                    .then((response) => {
                    this.fetchSessions();
                    this.successMessage = `Customer with email ${customerEmail} successfully unregistered for session with id ${sessionId}.`;
                    console.log(this.successMessage);
                    })
                    .catch((error) => {
                    // Handle error if needed
                    const errorMsg = error.response && error.response.data ? error.response.data : "Something went wrong";
                    this.successMessage = '';
                    console.error(`Error registering customer with email ${customerEmail} for session with id ${sessionId}:`, error);
                    this.error = errorMsg;
                    console.error(`Error unregistering customer with email ${customerEmail} for session with id ${sessionId}:`, error);
                    });

            });
        },

        onReset() {
            this.error = '';
            this.show = false;
            this.$nextTick(() => {
            this.show = true;
            })
        },

    },
    watch: {
        currentPage(newValue) {
        this.onPageChange(newValue);
        },
    },
};
