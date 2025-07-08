import React from 'react';
import '../../styles/landing.css';

const AboutSection = () => {
    return (
        <section id="about-us" className="section2">
            <h2>About Us</h2>
            <div className="section2-content">
                <div className="section2-text">
                    <p>Welcome to Pomodorio, your one-stop shop for fresh, high-quality food products delivered right to your door. Our mission is to provide you with the best selection of fresh fruits, vegetables, dairy, meat, and more - everything you need to maintain a healthy and balanced lifestyle.</p>
                    <p>At Pomodorio, we believe that good health starts with great food. That's why we're committed to making healthy eating convenient, affordable, and accessible for everyone. With easy online shopping, fast delivery, and excellent customer service, we strive to bring the best of the food world to your table.</p>
                    <p>Join us in exploring the world of fresh, nutritious, and delicious products - because your health and wellness are our top priorities!</p>
                </div>
                <div className="section2-image">
                    <img src="/images/section2.png" alt="About Pomodorio" />
                </div>
            </div>
        </section>
    );
};

export default AboutSection;