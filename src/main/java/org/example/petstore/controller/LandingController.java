package org.example.petstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    /**
     * Displays the landing page for authenticated users (USER role).
     *
     * @return the landing page view for users
     */
    @GetMapping("/user")
    public String getLandingUser() {
        return "landing/landingUSER";
    }

    /**
     * Displays the landing page for admin users (ADMIN role).
     *
     * @return the landing page view for admins
     */
    @GetMapping("/admin")
    public String getLandingAdmin() {
        return "landing/landingADMIN";
    }

    /**
     * Displays the landing page for unauthorized (guest) users.
     *
     * @return the landing page view for unauthorized users
     */
    @GetMapping("/")
    public String getLandingUnauthorized() {
        return "landing/landingUnauthorized";
    }
}
