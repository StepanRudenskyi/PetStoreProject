import React, { useState, useEffect } from "react";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/account.css";

const AccountPage = () => {
  const [accountInfo, setAccountInfo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, isAdmin } = useAuth();

  useEffect(() => {
    const fetchAccountInfo = async () => {
      try {
        setLoading(true);
        const data = await apiService.getAccountInfo();
        setAccountInfo(data);
        setError(null);
      } catch (err) {
        console.error("Failed to fetch account info:", err);
        setError("Failed to load account information. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchAccountInfo();
  }, []);

  if (!isAuthenticated()) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="account-section">
          <h2>Account Information</h2>
          <p className="unauthenticated">
            You need to be logged in to view your account information.
          </p>
        </section>
      </>
    );
  }

  if (loading) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="account-section">
          <h2>Account Information</h2>
          <p className="loading">Loading account information...</p>
        </section>
      </>
    );
  }

  if (error) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="account-section">
          <h2>Account Information</h2>
          <p className="error">{error}</p>
        </section>
      </>
    );
  }

  return (
    <>
      <Header
        isAuthenticated={isAuthenticated()}
        isAdmin={isAdmin()}
        variant="dark"
      />
      <section className="account-section">
        <h2>Account Information</h2>
        {accountInfo && (
          <div className="account-details">
            <div className="info-row">
              <span className="label">First Name:</span>
              <span className="value">{accountInfo.firstName}</span>
            </div>
            <div className="info-row">
              <span className="label">Last Name:</span>
              <span className="value">{accountInfo.lastName}</span>
            </div>
            <div className="info-row">
              <span className="label">Username:</span>
              <span className="value">{accountInfo.username}</span>
            </div>
            <div className="info-row">
              <span className="label">Email:</span>
              <span className="value">{accountInfo.email}</span>
            </div>
          </div>
        )}
      </section>
    </>
  );
};

export default AccountPage;
