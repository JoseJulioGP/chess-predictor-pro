import axios from 'axios';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  headers: { 'Content-Type': 'application/json' },
});

// Interceptor: añade el JWT en cada request si existe
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('ajedrez_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor de respuesta: si recibe 401, limpia el token (sesión expirada)
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('ajedrez_token');
      localStorage.removeItem('ajedrez_user');
    }
    return Promise.reject(error);
  }
);

export default apiClient;
