import apiClient from '../../shared/services/apiClient';

const authService = {
  register: async ({ email, username, password }) => {
    const { data } = await apiClient.post('/api/v1/auth/register', {
      email,
      username,
      password,
    });
    return data;
  },

  login: async ({ email, password }) => {
    const { data } = await apiClient.post('/api/v1/auth/login', {
      email,
      password,
    });
    return data;
  },

  fetchCurrentUser: async () => {
    const { data } = await apiClient.get('/api/v1/auth/me');
    return data;
  },
};

export default authService;
