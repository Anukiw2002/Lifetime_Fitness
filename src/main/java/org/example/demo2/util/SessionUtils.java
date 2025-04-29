package org.example.demo2.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class SessionUtils {



    public static boolean isUserAuthorized(HttpServletRequest request, HttpServletResponse response, String... allowedRoles) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return false;
        }

        String userRole = (String) session.getAttribute("userRole");
        for (String role : allowedRoles) {
            if (role.equals(userRole)) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/jsp/accessDenied.jsp");
        return false;
    }

}
