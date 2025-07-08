import React, { useEffect } from "react";
import Header from "../../components/layout/Header";
import HeroSection from "../../components/sections/HeroSection";
import AboutSection from "../../components/sections/AboutSection";
import { useLocation } from "react-router-dom"; // Import useLocation
import "../../styles/landing.css";

const LandingUser = () => {
  const location = useLocation(); // Get the current location object

  useEffect(() => {
    if (location.hash === "#about-us") {
      const aboutUsElement = document.getElementById("about-us");
      if (aboutUsElement) {
        aboutUsElement.scrollIntoView({ behavior: "smooth" });
      }
    }
  }, [location]);
  return (
    <>
      <section className="background-image">
        <Header isAuthenticated={true} isAdmin={false} />
        <HeroSection />
      </section>
      <AboutSection />
    </>
  );
};

export default LandingUser;
