import React from "react";
import googleLogo from "../../assets/G_logo.svg";

const GoogleSignInButton = () => (
  <a href="/oauth2/authorization/google" className="google-button">
    <img src={googleLogo} alt="Google logo" className="google-icon" />
    <span className="google-text">Log in with Google</span>
  </a>
);

export default GoogleSignInButton;
