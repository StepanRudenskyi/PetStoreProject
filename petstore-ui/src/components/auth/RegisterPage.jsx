import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Header from "../../components/layout/Header";
import "../../styles/RegisterPageNew.css";
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
          <div className="register-header">
            <h1>Create Account</h1>
            <p className="register-subtitle">Join us to get started</p>
          </div>

          {errorMessage && (
            <div
              className={`register-message ${
                errorMessage.includes("successful") ? "success" : "error"
              }`}
            >
              {errorMessage}
            </div>
          )}

          <form onSubmit={handleSubmit} className="register-form-content">
            <div className="form-row">
              <div className="form-group">
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  placeholder="First Name"
                  value={formData.firstName}
                  onChange={handleChange}
                  className={errors.firstName ? "error" : ""}
                />
                {errors.firstName && (
                  <span className="field-error">{errors.firstName}</span>
                )}
              </div>
              <div className="form-group">
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  placeholder="Last Name"
                  value={formData.lastName}
                  onChange={handleChange}
                  className={errors.lastName ? "error" : ""}
                />
                {errors.lastName && (
                  <span className="field-error">{errors.lastName}</span>
                )}
              </div>
            </div>

            <div className="form-group">
              <input
                type="email"
                id="email"
                name="email"
                placeholder="Email Address"
                value={formData.email}
                onChange={handleChange}
                className={errors.email ? "error" : ""}
              />
              <div className="email-notice">
                <svg
                  width="14"
                  height="14"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                >
                  <circle cx="12" cy="12" r="10"></circle>
                  <line x1="12" y1="8" x2="12" y2="12"></line>
                  <line x1="12" y1="16" x2="12.01" y2="16"></line>
                </svg>
                Your email cannot be changed after registration
              </div>
              {errors.email && (
                <span className="field-error">{errors.email}</span>
              )}
            </div>

            <div className="form-group">
              <input
                type="text"
                id="username"
                name="username"
                placeholder="Username"
                value={formData.username}
                onChange={handleChange}
                className={errors.username ? "error" : ""}
              />
              {errors.username && (
                <span className="field-error">{errors.username}</span>
              )}
            </div>

            <div className="form-group">
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Password"
                value={formData.password}
                onChange={handleChange}
                className={errors.password ? "error" : ""}
              />
              {errors.password && (
                <span className="field-error">{errors.password}</span>
              )}
            </div>

            <button type="submit" className="register-button">
              Create Account
            </button>
          </form>

          <div className="register-footer">
            <p>
              Already have an account?{" "}
              <Link to="/login" className="login-link">
                Sign in
              </Link>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
