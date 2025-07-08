import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import Header from "../../components/layout/Header";
import "../../styles/LogoutPage.css";

const LogoutPage = () => {
  const { logout, isAuthenticated } = useAuth();
  const [message, setMessage] = useState(
    "You have been successfully logged out."
  );
  const [showLoginLink, setShowLoginLink] = useState(false);

  useEffect(() => {
    const performLogout = async () => {
      if (isAuthenticated()) {
        await logout();
      }
      setMessage("You have been successfully logged out.");

      setShowLoginLink(true);
    };
    performLogout();
  }, [logout, isAuthenticated]);

  return (
    <>
      <Header isAuthenticated={false} isAdmin={false} variant="dark" />

      <div className="logout-container">
        <div className="logout-content">
          <h1 className="logout-title">Goodbye!</h1>
          <p className="logout-message">{message}</p>
          {showLoginLink && (
            <Link to="/login" className="login-again-button">
              Login Again
            </Link>
          )}
          <p className="redirect-note">
            You can also navigate to any other page.
          </p>

          <Link to="/" className="back-to-landing">
            Return to Home Page
          </Link>
        </div>
      </div>
    </>
  );
};

export default LogoutPage;
