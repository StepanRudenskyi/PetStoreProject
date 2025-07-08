import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/landing.css';

const HeroSection = () => {
    return (
        <div className="section1">
            <div className="section1-content">
                <p>Immerse yourself in the world of fresh products, take care of your health and beauty with us.</p>
                <Link to="/products/categories" className="btn-view-products">View all departments</Link>
            </div>
        </div>
    );
};

export default HeroSection;