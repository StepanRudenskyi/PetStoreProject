import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/landing.css';

const Sidebar = () => {
    return (
        <div className="sidebar">
            <nav>
                <ul>
                    <li><Link to="/products/categories">Departments</Link></li>
                    <li><Link to="/account">Account</Link></li>
                    <li><Link to="/admin/statistics">Statistics</Link></li>
                    <li><Link to="/admin/users">Manage Users</Link></li>
                    <li><Link to="/admin/orders">Manage Orders</Link></li>
                    <li><Link to="/user">Switch to User</Link></li>
                    <li><Link to="/cart">Cart</Link></li>
                    <li><Link to="/logout">Logout</Link></li>
                </ul>
            </nav>
        </div>
    );
};

export default Sidebar;