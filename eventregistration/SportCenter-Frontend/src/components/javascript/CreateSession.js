import axios from "axios";
import config from "../../../config";

const AXIOS = axios.create({
  baseURL: `http://${config.dev.backendHost}:${config.dev.backendPort}`,
  headers: { "Access-Control-Allow-Origin": `http://${config.dev.host}:${config.dev.port}` },
});

