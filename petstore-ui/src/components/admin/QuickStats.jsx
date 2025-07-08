import React, { useEffect, useState } from "react";
import { Users, ShoppingCart, TrendingUp, AlertCircle } from "lucide-react";
import "./QuickStats.css";
import { apiService } from "../../services/api";

const iconMapper = {
  "Total Users": Users,
  "Orders Today": ShoppingCart,
  "Revenue": TrendingUp,
  "Pending Orders": AlertCircle,
};

const QuickStats = () => {
  const [stats, setStats] = useState([]);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await apiService.getQuickStats();
        setStats(response.stats);
      } catch (error) {
        console.error("Error fetching quick stats: ", error);
      }
    };

    fetchStats();
  }, []);


  return (
    <div className="quick-stats">
      <h2>Overview</h2>
      <div className="stats-grid">
        {stats.map((stat, index) => {
          const IconComponent = iconMapper[stat.label] || Users; // default icon
          return (
            <div key={index} className="stat-card">
              <div className="stat-icon">
                <IconComponent size={24} />
              </div>
              <div className="stat-content">
                <div className="stat-value">
                  {stat.currency === "USD" ? `$${stat.value.toLocaleString()}` : stat.value}
                </div>
                <div className="stat-label">{stat.label}</div>
                <div className={`stat-change ${stat.change >= 0 ? "positive" : "negative"}`}>
                  {stat.change > 0 ? "+" : ""}
                  {stat.change}
                  {stat.unit === "percent" && "%"}
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default QuickStats;
