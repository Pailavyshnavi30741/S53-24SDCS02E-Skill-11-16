import axios from "axios";

const BASE_URL = "http://localhost:8080/api/users";

export const loginUser = (data) => {
  return axios.post(`${BASE_URL}/login`, {
    username: data.username,
    password: data.password,
  });
};

export const registerUser = (data) => {
  return axios.post(`${BASE_URL}/register`, data);
};