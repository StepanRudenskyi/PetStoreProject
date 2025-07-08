import React from "react";
import "./AdminHeader.css";

const AdminHeader = () => {
  return (
    <header className="admin-header">
      <div className="admin-header-content">
        <a href="/admin" className="admin-logo">
          <h1>Pomodorio Admin</h1>
        </a>
        <nav className="admin-nav">
          <a href="/logout" className="logout-btn">
            Logout
          </a>
        </nav>
      </div>
    </header>
  );
};

export default AdminHeader;
