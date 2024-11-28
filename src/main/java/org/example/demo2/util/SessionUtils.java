package org.example.demo2.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class SessionUtils {

    // Method to check if the user is authorized
    public static boolean isUserAuthorized(HttpServletRequest request, HttpServletResponse response, String requiredRole) throws IOException {
        HttpSession session = request.getSession(false);  // Use 'false' to avoid creating a new session
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return false;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (requiredRole.equals(userRole)) {
            return true; // Authorized user, proceed with the page
        } else {
            // Unauthorized user, redirect to access denied page
            response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
            return false;
        }
    }
}
