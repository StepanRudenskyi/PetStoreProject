import React from "react";
import "./AdminLanding.css";
import RecentActivity from "../../components/admin/RecentActivity";
import AdminHeader from "../../components/layout/AdminHeader";
import QuickStats from "../../components/admin/QuickStats";
import AdminActions from "../../components/admin/AdminActions";

const AdminLanding = () => {
  return (
    <div className="admin-dashboard">
      <AdminHeader />
      <main className="admin-main">
        <div className="admin-container">
          <div className="welcome-section">
            <h1>Welcome back, Admin</h1>
            <p>Here's what's happening with your store today.</p>
          </div>
          <QuickStats />
          <AdminActions />
          <RecentActivity />
        </div>
      </main>
    </div>
  );
};

export default AdminLanding;
