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

function SessionDto(
  id,
  startTime,
  endTime,
  courseName,
  locationName
) {
  this.id = id;
  this.startTime = startTime;
  this.endTime = endTime;
  this.courseName = courseName;
  this.locationName = locationName;
}

export default {
  name: "createSessionAdmin",
  data() {
    return {
      form: {
        id: "",
        startTime: "",
        endTime: "",
        courseName: "",
        locationName: "",
      },
      show: true,
      error: "",
      successMessage: "",
    };
  },
  methods: {
    convertToDateTime(inputedDateTime) {
      const dateTimeParts = inputedDateTime.split(' ');
      const date = dateTimeParts[0];
      const time = dateTimeParts[1];
      return `${date}T${time}:00`;
    },
     onLogout() {
      logout();
     this.$router.push("/login");
    },
    createSession() {
      if (this.form.startTime === "") {
        this.error = "startTime is required";
      } else if (this.form.endTime === "") {
        this.error = "endTime is required";
      } else if (this.form.courseName === "") {
        this.error = "Course name is required";
      } else if (this.form.locationName === "") {
        this.error = "Location name is required";
      } else {
        // Create a new instance of SessionDto
        const formData = new URLSearchParams();
        formData.append("id", this.id);
        formData.append("startTime", this.convertToDateTime(this.form.startTime));
        formData.append("endTime", this.convertToDateTime(this.form.endTime));
        formData.append("courseName", this.form.courseName);
        formData.append("locationName", this.form.locationName);

        AXIOS.post(`/session`, formData)
          .then((response) => {
            console.log(response.data);
            this.successMessage = "Session created successfully";
            this.error = "";
            setTimeout(() => {
              this.$router.push("DisplaySessions");
            }, 300);
          })
          .catch((e) => {
            const errorMsg =
              e.response && e.response.data
                ? e.response.data
                : "something went wrong";
            console.error(errorMsg);
            this.error = errorMsg;
          });
      }
    },
    // Reset error message
    resetError() {
      this.error = "";
      this.successMessage = "";
    },
    resetSucces() {
      this.successMessage = "";
    },
    onSubmit() {
      this.createSession();
    },
    onReset() {
     this.form = {
        id:"",
        startTime: "",
        endTime: "",
        courseName: "",
        locationName: "",
      };
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onReturn() {
      console.log("Returning to DisplayCourse");
      this.$router.push("DisplaySessions");
    },
  },
};
