import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Header from "../../components/layout/Header";
import "../../styles/auth.css";
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
  const { login } = useAuth(); // Assuming you have a login function in your AuthContext

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
        // Registration successful, you might want to log the user in immediately
        // or redirect them to a success page.
        console.log("Registration successful:", response);
        setErrorMessage("Registration successful! Redirecting to login...");
        // Optionally log in the user after registration
        await login(formData.username, formData.password);
        navigate("/products"); // Or wherever you want to redirect after registration/login
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
      <div className="auth-container">
        <div className="auth-form">
          <h2>Register now</h2>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              {errorMessage && <div className="auth-error">{errorMessage}</div>}
              <label htmlFor="firstName">First Name:</label>
              <input
                type="text"
                id="firstName"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
              />
              {errors.firstName && (
                <div className="auth-error">{errors.firstName}</div>
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
                <div className="auth-error">{errors.lastName}</div>
              )}
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
              {errors.email && <div className="auth-error">{errors.email}</div>}
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
                <div className="auth-error">{errors.username}</div>
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
                <div className="auth-error">{errors.password}</div>
              )}
            </div>

            <button type="submit" className="auth-button">
              Register
            </button>
          </form>
          <div className="auth-links">
            <p>
              Already registered? <Link to="/login">Log in here</Link>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
