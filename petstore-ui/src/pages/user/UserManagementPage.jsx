import React, { useState, useEffect, useCallback } from "react";
import AdminHeader from "../../components/layout/AdminHeader";
import { apiService } from "../../services/api";
import { Link } from "react-router-dom";
import {
  Edit,
  Shield,
  ChevronLeft,
  ChevronRight,
  Loader2, // For loading spinner
  Ban, // For demote
  CheckCircle, // For success messages
  XCircle, // For error messages
} from "lucide-react";
import "../../styles/UserManagementPage.css";
import PaginationComponent from "../../components/common/PaginationComponent";

const UserManagementPage = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [message, setMessage] = useState(null);

  // Fetch users with pagination
  const fetchUsers = useCallback(async () => {
    setLoading(true);
    setError(null);
    setMessage(null); // Clear previous messages
    try {
      const response = await apiService.getUsers(currentPage, pageSize);
      if (response && response.content) {
        setUsers(response.content);
        setTotalPages(response.totalPages);
      } else {
        setUsers([]);
        setTotalPages(0);
        setError("No users found or an unexpected response format.");
      }
    } catch (err) {
      console.error("Failed to fetch users:", err);
      setError(
        err.response?.data?.message || "Failed to load users. Please try again."
      );
      setUsers([]);
      setTotalPages(0);
    } finally {
      setLoading(false);
    }
  }, [currentPage, pageSize]);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  // Handle pagination clicks
  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage);
    }
  };

  // Handle promote/demote user
  const handlePromoteDemote = async (userId, currentRoles) => {
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      if (currentRoles.includes("ADMIN")) {
        // Demote user
        await apiService.removeAdmin(userId);
        setMessage({
          type: "success",
          text: `User ${userId} demoted from admin.`,
        });
      } else {
        // Promote user
        await apiService.addAdmin(userId);
        setMessage({
          type: "success",
          text: `User ${userId} promoted to admin.`,
        });
      }
      // Re-fetch users to update roles
      await fetchUsers();
    } catch (err) {
      console.error("Error promoting/demoting user:", err);
      setMessage({
        type: "error",
        text:
          err.response?.data?.message ||
          `Failed to update role for user ${userId}.`,
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="admin-dashboard">
      <AdminHeader />
      <div className="user-management-page">
        <div className="welcome-section">
          <h1>User Management</h1>
          <p>View, edit, and manage user accounts in your system.</p>
        </div>

        {message && (
          <div
            className={`alert-message ${
              message.type === "success" ? "alert-success" : "alert-error"
            }`}
          >
            {message.type === "success" ? (
              <CheckCircle size={20} />
            ) : (
              <XCircle size={20} />
            )}
            <span>{message.text}</span>
          </div>
        )}

        {loading && (
          <div className="loading-indicator">
            <Loader2 className="spinner" size={36} />
            <p>Loading users...</p>
          </div>
        )}

        {error && <div className="error-message">{error}</div>}

        {!loading && !error && users.length === 0 && (
          <div className="no-users-found">
            <p>No users found.</p>
          </div>
        )}

        {!loading && !error && users.length > 0 && (
          <>
            <div className="user-table-wrapper">
              <table className="user-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Roles</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((user) => {
                    // Determine if the user is an admin by checking the string roles directly
                    const isAdmin = user.roles && user.roles.includes("ADMIN");

                    return (
                      <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                        {/* Roles Display */}
                        <td>
                          {user.roles && user.roles.length > 0
                            ? user.roles
                                .map((role) => {
                                  if (
                                    typeof role === "object" &&
                                    role !== null &&
                                    role.name
                                  ) {
                                    return role.name;
                                  }
                                  if (typeof role === "string") {
                                    return role;
                                  }
                                  return null;
                                })
                                .filter(Boolean)
                                .join(", ")
                            : "N/A"}
                        </td>
                        <td>
                          <div className="user-actions-cell">
                            {/* Example: Edit user link */}
                            {/* <Link
                              to={`/admin/users/${user.id}/edit`}
                              className="action-button edit-button"
                              title="Edit User"
                            >
                              <Edit size={18} />
                            </Link> */}

                            {/* Promote/Demote Button */}
                            <button
                              onClick={() =>
                                handlePromoteDemote(user.id, user.roles)
                              }
                              className={`action-button ${
                                isAdmin ? "demote-button" : "promote-button"
                              }`}
                              title={
                                isAdmin
                                  ? "Demote from Admin"
                                  : "Promote to Admin"
                              }
                            >
                              {/* Icon */}
                              {isAdmin ? (
                                <Ban size={18} />
                              ) : (
                                <Shield size={18} />
                              )}
                              {/* Text Label */}
                              <span style={{ marginLeft: "5px" }}>
                                {isAdmin ? "Demote" : "Promote"}
                              </span>
                            </button>
                          </div>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>

            {totalPages > 1 && (
              <PaginationComponent
                count={totalPages}
                page={currentPage + 1}
                onChange={(event, value) => setCurrentPage(value - 1)}
              />

              // <div className="pagination-controls">
              //   <button
              //     onClick={() => handlePageChange(currentPage - 1)}
              //     disabled={currentPage === 0}
              //     className="pagination-button"
              //   >
              //     <ChevronLeft size={20} /> Previous
              //   </button>
              //   <span className="pagination-info">
              //     Page {currentPage + 1} of {totalPages}
              //   </span>
              //   <button
              //     onClick={() => handlePageChange(currentPage + 1)}
              //     disabled={currentPage === totalPages - 1}
              //     className="pagination-button"
              //   >
              //     Next <ChevronRight size={20} />
              //   </button>
              // </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default UserManagementPage;
