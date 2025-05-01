import React from 'react';
import Header from '../../components/layout/Header';
import HeroSection from '../../components/sections/HeroSection';
import AboutSection from '../../components/sections/AboutSection';
import '../../styles/landing.css';

const LandingUnauthorized = () => {
    return (
        <>
            <section className="background-image">
                <Header isAuthenticated={false} isAdmin={false} />
                <HeroSection />
            </section>
            <AboutSection />
        </>
    );
};

export default LandingUnauthorized;