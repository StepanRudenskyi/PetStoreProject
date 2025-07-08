// AccountPage.js
import React, { useState, useEffect } from "react";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/account.css";

const AccountPage = () => {
  const [accountInfo, setAccountInfo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null); // This holds the error message text
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
  });
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [updateSuccess, setUpdateSuccess] = useState(false);
  const [showError, setShowError] = useState(false); // New state to control error message visibility

  const { isAuthenticated, isAdmin, logout } = useAuth();

  useEffect(() => {
    const fetchAccountInfo = async () => {
      try {
        setLoading(true);
        setError(null); // Clear any previous errors
        setShowError(false); // Hide previous error message
        const data = await apiService.getAccountInfo();
        setAccountInfo(data);
        setFormData({
          firstName: data.firstName || "",
          lastName: data.lastName || "",
          username: data.username || "",
          email: data.email || "",
        });
      } catch (err) {
        console.error("Failed to fetch account info:", err);
        setError("Failed to load account information. Please try again later.");
        setShowError(true);
        setTimeout(() => setShowError(false), 3000); // Make error disappear after 3 seconds
      } finally {
        setLoading(false);
      }
    };

    if (isAuthenticated()) {
      fetchAccountInfo();
    }
  }, [isAuthenticated]);

  const handleEditClick = () => {
    setIsEditing(true);
    setUpdateSuccess(false); // Reset success message when starting to edit
    setShowError(false); // Hide error message when starting to edit
    setError(null); // Clear error message text
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    // Reset formData to original accountInfo if editing is cancelled
    setFormData({
      firstName: accountInfo.firstName || "",
      lastName: accountInfo.lastName || "",
      username: accountInfo.username || "",
      email: accountInfo.email || "",
    });
    setShowError(false); // Hide error message when cancelling
    setError(null); // Clear error message text
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();

    // Prevent multiple simultaneous requests
    if (loading) {
      return;
    }

    try {
      setLoading(true);
      setError(null);
      setShowError(false); // Hide previous error message

      const dataToSend = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        username: formData.username,
        // Don't send email since it's not updatable
      };

      const updatedAccountData = await apiService.updateAccountInfo(dataToSend);
      setAccountInfo(updatedAccountData); // Update local state with new data
      setFormData({
        firstName: updatedAccountData.firstName,
        lastName: updatedAccountData.lastName,
        username: updatedAccountData.username,
        email: updatedAccountData.email, // Keep email for display but don't send for update
      });
      setIsEditing(false); // Exit edit mode
      setUpdateSuccess(true); // Show success message
      setTimeout(() => setUpdateSuccess(false), 2500); // Hide success message after 2.5 seconds
    } catch (err) {
      console.error("Failed to update account info:", err);
      // Handle specific error messages from backend
      const errorMessage =
        err.response?.data?.message ||
        err.message ||
        "Failed to update account information. Please try again.";
      setError(errorMessage);
      setShowError(true);
      setTimeout(() => setShowError(false), 2500); // Make error disappear after 2.5 seconds
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteAccount = async () => {
    if (!accountInfo || !accountInfo.id) {
      setError(
        "Account ID not available for deletion. Please refresh the page."
      );
      setShowError(true);
      setTimeout(() => setShowError(false), 3000);
      return;
    }
    try {
      setLoading(true);
      setError(null);
      setShowError(false); // Hide previous error message
      await apiService.deleteAccount(accountInfo.id);
      logout(); // Log out the user after successful deletion
      // Optionally redirect to home or login page
      window.location.href = "/";
    } catch (err) {
      console.error("Failed to delete account:", err);
      setError("Failed to delete account. Please try again.");
      setShowError(true);
      setTimeout(() => setShowError(false), 2500); // Make error disappear after 2.5 seconds
    } finally {
      setLoading(false);
      setShowDeleteConfirmation(false); // Close the modal
    }
  };

  // Conditional rendering for the initial loading/error states
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

  // Initial loading state (only when accountInfo is null)
  if (loading && !accountInfo) {
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

  // Initial error state (only when accountInfo is null and there was an error fetching)
  // This 'error' is for the initial fetch, not specific update/delete errors
  if (error && !accountInfo && !showError) {
    // Add !showError here to prevent showing a lingering error
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

        {/* Display error message if showError is true and error message exists */}
        {showError && error && <p className="error-message">{error}</p>}
        {updateSuccess && (
          <p className="success-message">
            Account information updated successfully!
          </p>
        )}

        {accountInfo && (
          <div className="account-details">
            {!isEditing ? (
              <>
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
                <div className="account-actions">
                  <button className="update-button" onClick={handleEditClick}>
                    Update Account
                  </button>
                  <button
                    className="delete-button"
                    onClick={() => setShowDeleteConfirmation(true)}
                  >
                    Delete Account
                  </button>
                </div>
              </>
            ) : (
              <form onSubmit={handleUpdateSubmit} className="update-form">
                <div className="form-group">
                  <label htmlFor="firstName">First Name:</label>
                  <input
                    type="text"
                    id="firstName"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="lastName">Last Name:</label>
                  <input
                    type="text"
                    id="lastName"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="username">Username:</label>
                  <input
                    type="text"
                    id="username"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="email">Email:</label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    readOnly
                    disabled
                    style={{
                      backgroundColor: "#f5f5f5",
                      cursor: "not-allowed",
                    }}
                  />
                  <small
                    style={{
                      color: "#666",
                      fontSize: "12px",
                      marginTop: "4px",
                      display: "block",
                    }}
                  >
                    You cannot change your email
                  </small>
                </div>
                <div className="form-actions">
                  <button
                    type="submit"
                    className="save-button"
                    disabled={loading}
                  >
                    {loading ? "Saving..." : "Save Changes"}
                  </button>
                  <button
                    type="button"
                    className="cancel-button"
                    onClick={handleCancelEdit}
                    disabled={loading}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            )}
          </div>
        )}
      </section>

      {showDeleteConfirmation && (
        <div className="modal-overlay">
          <div className="delete-confirmation-modal">
            <h3>Confirm Account Deletion</h3>
            <p>
              Are you sure you want to delete your account? This action cannot
              be undone.
            </p>
            <div className="modal-actions">
              <button
                className="confirm-delete-button"
                onClick={handleDeleteAccount}
                disabled={loading}
              >
                {loading ? "Deleting..." : "Yes, Delete"}
              </button>
              <button
                className="cancel-delete-button"
                onClick={() => setShowDeleteConfirmation(false)}
                disabled={loading}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default AccountPage;
