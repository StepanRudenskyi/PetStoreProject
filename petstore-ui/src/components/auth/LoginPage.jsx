import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/RegisterPageNew.css";
import GoogleSignInButton from "./GoogleSignInButton ";
import Header from "../layout/Header";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login, isAdmin, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const result = await login({ username, password });
      if (result.success) {
        if (isAdmin()) {
          navigate("/admin");
        } else {
          navigate("/user");
        }
      } else {
        setError(result.error || "Login failed");
      }
    } catch (err) {
      setError("An error occurred during login");
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
            <h2>Login to Pomodorio</h2>
            <p className="register-subtitle">Log in to start shopping</p>
          </div>

          {error && <div className="auth-error">{error}</div>}

          <form onSubmit={handleSubmit} className="register-form-content">
            <div className="form-group">
              <input
                type="text"
                id="username"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Passwors"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="register-button">
              Login
            </button>
            <GoogleSignInButton />
          </form>

          <div className="register-footer">
            <p>
              Don't have an account?{" "}
              <Link to="/register" className="login-link">
                Register
              </Link>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginPage;
