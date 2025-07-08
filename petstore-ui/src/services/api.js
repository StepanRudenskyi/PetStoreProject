import axios from "axios";

const API_BASE_URL =
  process.env.REACT_APP_API_URL || "https://localhost:8443/api";

// const API_BASE_URL = "/api";

// const getAuthToken = () => localStorage.getItem("token");

// Create an axios instance with a base URL
const api = axios.create({
  baseURL: API_BASE_URL,
});

// Request interceptor to add the JWT token to every request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (
      token &&
      !config.url.endsWith("/auth/login") &&
      !config.url.endsWith("/auth/register")
    ) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const apiService = {
  // Auth endpoints
  login: async (credentials) => {
    try {
      const response = await api.post("/auth/login", credentials);
      return response.data;
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  },
  // login: async (credentials) => {
  //   try {
  //     const response = await fetch(`${API_BASE_URL}/auth/login`, {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //       body: JSON.stringify(credentials),
  //     });

  //     if (!response.ok) {
  //       const errorData = await response.json();
  //       throw new Error(
  //         errorData.message || `Login failed with status: ${response.status}`
  //       );
  //     }

  //     return await response.json();
  //   } catch (error) {
  //     console.error("Login error (fetch):", error);
  //     throw error;
  //   }
  // },

  logout: async () => {
    try {
      const response = await api.post("/auth/logout");
      return response.data;
    } catch (error) {
      console.error("Logout error:", error);
      throw error;
    }
  },

  // getCurrentUser: async () => {
  //   try {
  //     const response = await api.get("/account/info");
  //     return response.data;
  //   } catch (error) {
  //     console.error("Get current user error:", error);
  //     throw error;
  //   }
  // },

  // Product endpoints
  getProductCategories: async () => {
    try {
      const response = await api.get("/categories");
      return response.data;
    } catch (error) {
      console.error("Get categories error:", error);
      throw error;
    }
  },

  getProductsByCategory: async (categoryId) => {
    try {
      const response = await api.get(`/products/categories/${categoryId}`);
      return response.data;
    } catch (error) {
      console.error("Get products by category error:", error);
      throw error;
    }
  },

  // Cart endpoints
  getCart: async () => {
    // return mockCart;
    try {
      const response = await api.get("/cart");
      return response.data;
    } catch (error) {
      console.error("Get cart error:", error);
      throw error;
    }
  },

  addToCart: async (productId, quantity) => {
    try {
      const response = await api.post(
        `/cart/add?productId=${productId}&quantity=${quantity}`,
        {} // Axios requires a body even for query params in POST
      );
      return response.data;
    } catch (error) {
      console.error("Add to cart error:", error);
      throw error;
    }
  },

  removeFromCart: async (productId) => {
    try {
      await api.delete(`/cart/remove/${productId}`);
    } catch (error) {
      console.error(
        `Error removing product with id: ${productId} form cart: `,
        error
      );
      throw error;
    }
  },

  getCheckoutData: async () => {
    try {
      const response = await api.get("/checkout");
      return response.data;
    } catch (error) {
      console.error("Error fetching checkout data:", error);
      throw error;
    }
  },

  processCheckout: async (paymentMethod) => {
    try {
      const response = await api.post(
        `/checkout/process?paymentMethod=${paymentMethod}`
      );
      return response.data;
    } catch (error) {
      console.error("Error processing checkout:", error);
      throw error;
    }
  },

  // Account endpoints
  getAccountInfo: async () => {
    // return mockAccountInfo;
    try {
      const response = await api.get("/account/info");
      return response.data;
    } catch (error) {
      console.error("Get account info error: ", error);
      throw error;
    }
  },

  register: async (userData) => {
    try {
      const response = await api.post("/auth/register", userData);
      return response.data;
    } catch (error) {
      console.error("Registration API error:", error);
      throw error;
    }
  },

  updateAccountInfo: async (userData) => {
    try {
      const response = await api.put(`/account`, userData);
      return response.data;
    } catch (error) {
      console.error("Error updating account info: ", error);
      throw error;
    }
  },

  deleteAccount: async (accountId) => {
    try {
      const response = await api.delete(`/account/${accountId}`);
      return response.data;
    } catch (error) {
      console.error("Error deleting account: ", error);
      throw error;
    }
  },

  // User Management
  getUsers: async (page = 0, size = 5) => {
    const response = await api.get(`/admin/users`, {
      params: { page, size },
    });
    return response.data; // Backend returns Page object directly
  },

  addAdmin: async (userId) => {
    const response = await api.post(`/admin/add-admin/${userId}`);
    return response.data;
  },

  removeAdmin: async (userId) => {
    const response = await api.delete(`/admin/remove-admin/${userId}`);
    return response.data;
  },

  // Manage Orders
  getOrders: async (page = 0, size = 10) => {
    const response = await api.get(`/admin/orders`, {
      params: { page, size },
    });
    return response.data;
  },

  getOrdersByStatus: async (status, page = 0, size = 10) => {
    const response = await api.get(`/admin/orders`, {
      params: { status, page, size },
    });
    return response.data;
  },

  updateOrderStatus: async (orderId, status) => {
    console.log("entered updateOrderStatus in api.js");
    await api.patch(`/admin/orders/${orderId}/status`, status);
  },

  getQuickStats: async () => {
    const response = await api.get(`/admin/stats`);
    console.log(response.data);
    return response.data;
  },
};
