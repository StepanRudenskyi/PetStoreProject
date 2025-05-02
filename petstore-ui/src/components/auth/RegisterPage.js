import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Header from "../../components/layout/Header";
import "../../styles/RegisterPageNew.css"; // Import the new CSS file
import { apiService } from "../../services/api";
import { useAuth } from "../../contexts/AuthContext";

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
  });
  const [errors, setErrors] = useState({});
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const { login, isAuthenticated } = useAuth();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setErrorMessage("");

    try {
      const response = await apiService.register(formData);
      if (response.success) {
        console.log("Registration successful:", response);
        setErrorMessage("Registration successful! Redirecting to login...");
        await login({
          username: formData.username,
          password: formData.password,
        });
        navigate("/products");
      } else if (response.message) {
        setErrorMessage(response.message);
      } else {
        setErrorMessage("Registration failed. Please try again.");
      }
    } catch (error) {
      console.error("Registration error:", error);
      if (error.response && error.response.data && error.response.data.errors) {
        setErrors(error.response.data.errors);
      } else if (error.message) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unexpected error occurred during registration.");
      }
    }
  };

  return (
    <>
      <Header
        isAuthenticated={isAuthenticated()}
        isAdmin={false}
        variant="dark"
      />
      <div className="register-container">
        <div className="register-form">
          <h2>Create Your Account</h2>
          {errorMessage && <div className="register-error">{errorMessage}</div>}
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="firstName">First Name:</label>
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                />
                {errors.firstName && (
                  <div className="register-error">{errors.firstName}</div>
                )}
              </div>
              <div className="form-group">
                <label htmlFor="lastName">Last Name:</label>
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                />
                {errors.lastName && (
                  <div className="register-error">{errors.lastName}</div>
                )}
              </div>
            </div>

            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
              />
              {errors.email && (
                <div className="register-error">{errors.email}</div>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
              />
              {errors.username && (
                <div className="register-error">{errors.username}</div>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
              />
              {errors.password && (
                <div className="register-error">{errors.password}</div>
              )}
            </div>

            <button type="submit" className="register-button">
              Register
            </button>
          </form>
          <div className="register-links">
            <p>
              Already have an account?{" "}
              <Link
                to="/login"
                style={{ color: "#85baa1", textDecoration: "underline" }}
              >
                Log in here
              </Link>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
