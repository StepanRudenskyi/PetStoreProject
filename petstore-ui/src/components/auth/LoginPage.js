import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/auth.css";
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
        console.log("------- Is Admin?: ", isAdmin());
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
      <div className="auth-container">
        <div className="auth-form">
          <h2>Login to Pomodorio</h2>
          {error && <div className="auth-error">{error}</div>}
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">Username</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="auth-button">
              Login
            </button>
          </form>
          <div className="auth-links">
            <p>Or</p>
            <GoogleSignInButton />
            <p>
              Don't have an account?{" "}
              <Link
                to="/register"
                style={{ color: "#85baa1", textDecoration: "underline" }}
              >
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
