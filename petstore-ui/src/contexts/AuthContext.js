import React, { createContext, useContext, useState, useEffect } from "react";
import { apiService } from "../services/api";

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const storedToken = localStorage.getItem("token");

    if (storedUser && storedToken) {
      setCurrentUser(JSON.parse(storedUser));
      setToken(storedToken);
    }

    setLoading(false);
  }, []);

  const login = async (credentials) => {
    try {
      const response = await apiService.login(credentials);
      console.log("---- Login Response: ", response);

      if (response && response.token && response.roles) {
        const { token: newToken, roles } = response;
        const userData = { ...response, roles }; // Include roles in userData

        // Store auth info
        setCurrentUser(userData);
        setToken(newToken);
        localStorage.setItem("user", JSON.stringify(userData));
        localStorage.setItem("token", newToken);

        return { success: true };
      } else {
        return {
          success: false,
          error: response?.message || "Login failed to return a token",
        };
      }
    } catch (error) {
      console.error("Login failed", error);
      return { success: false, error: "Login failed" };
    }
  };

  const logout = () => {
    setCurrentUser(null);
    setToken(null);
    localStorage.removeItem("user");
    localStorage.removeItem("authHeader");
    return { success: true };
  };

  const isAuthenticated = () => {
    return !!currentUser && !!token;
  };

  const isAdmin = () => {
    console.log("Current user roles: ", currentUser.roles);
    return currentUser?.roles?.includes("ROLE_ADMIN") || false;
  };

  const value = {
    currentUser,
    token,
    isAuthenticated,
    isAdmin,
    login,
    logout,
    loading,
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
