import React from 'react';
import './RecentActivity.css';

const RecentActivity = () => {
  const activities = [
    { action: 'New order received', details: 'Order #1234 - $125.50', time: '2 minutes ago' },
    { action: 'User registered', details: 'john.doe@email.com', time: '15 minutes ago' },
    { action: 'Product updated', details: 'Fresh Apples - Stock: 50', time: '1 hour ago' },
    { action: 'Order shipped', details: 'Order #1230 - Delivered', time: '2 hours ago' }
  ];

  return (
    <div className="recent-activity">
      <h2>Recent Activity</h2>
      <div className="activity-list">
        {activities.map((activity, index) => (
          <div key={index} className="activity-item">
            <div className="activity-content">
              <div className="activity-action">{activity.action}</div>
              <div className="activity-details">{activity.details}</div>
            </div>
            <div className="activity-time">{activity.time}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RecentActivity;
