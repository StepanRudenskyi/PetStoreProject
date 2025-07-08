import React from "react";
import {
  Users,
  ShoppingCart,
  Package,
  BarChart3,
  Settings,
} from "lucide-react";
import "./AdminActions.css";

const AdminActions = () => {
  const actions = [
    {
      title: "User Management",
      description: "View, edit, and manage user accounts",
      icon: Users,
      link: "/admin/users",
      actions: [{ label: "View All Users", link: "/admin/users" }],
    },
    {
      title: "Order Management",
      description: "Process and track customer orders",
      icon: ShoppingCart,
      link: "/admin/orders",
      actions: [
        { label: "View All Orders", link: "/admin/orders" },
        // { label: "Pending Orders", link: "/admin/orders/pendings" },
      ],
    },
    {
      title: "Product Management",
      description: "Manage inventory and product catalog",
      icon: Package,
      link: "/admin/products",
      actions: [
        { label: "View Products", link: "/admin/products" },
        // { label: "Add New Product", link: "/admin/products/new" },
      ],
    },
    {
      title: "Analytics & Reports",
      description: "View sales statistics and reports",
      icon: BarChart3,
      link: "/admin/statistics",
      actions: [
        { label: "View Analytics", link: "/admin/statistics" },
        // { label: "Generate Report", link: "/admin/reports" },
      ],
    },
    {
      title: "Categories",
      description: "Organize product departments",
      icon: Settings,
      link: "/admin/categories",
      actions: [{ label: "Manage Categories", link: "/admin/categories" }],
    },
  ];

  return (
    <div className="admin-actions">
      <h2>Quick Actions</h2>
      <div className="actions-grid">
        {actions.map((action, index) => {
          const IconComponent = action.icon;
          return (
            <div key={index} className="action-card">
              <div className="action-header">
                <div className="action-icon">
                  <IconComponent size={32} />
                </div>
                <div className="action-info">
                  <h3>{action.title}</h3>
                  <p>{action.description}</p>
                </div>
              </div>
              <div className="action-buttons">
                {action.actions.map((btn, btnIndex) => (
                  <a key={btnIndex} href={btn.link} className="action-btn">
                    {btn.label}
                  </a>
                ))}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default AdminActions;
