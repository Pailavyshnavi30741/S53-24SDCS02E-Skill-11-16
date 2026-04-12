// src/services/api.js
// Central API configuration - reads from environment variables

import axios from 'axios';

// Base URL comes from environment variable (set in .env files)
const BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor - add auth token if needed
api.interceptors.request.use(
  (config) => {
    console.log(`API Request: ${config.method?.toUpperCase()} ${config.url}`);
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor - handle errors globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.message);
    return Promise.reject(error);
  }
);

// ─── API Functions ────────────────────────────────────────────────────────────

export const checkHealth = () => api.get('/health');

export const getProducts = () => api.get('/products');

export const getProductById = (id) => api.get(`/products/${id}`);

export const createProduct = (product) => api.post('/products', product);

export default api;
