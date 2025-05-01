import React from 'react';
import Header from '../../components/layout/Header';
import Sidebar from '../../components/layout/Sidebar';
import HeroSection from '../../components/sections/HeroSection';
import '../../styles/landing.css';

const LandingAdmin = () => {
    return (
        <section className="background-image">
            <Header isAuthenticated={true} isAdmin={true} />
            <Sidebar />
            <HeroSection />
        </section>
    );
};

export default LandingAdmin;