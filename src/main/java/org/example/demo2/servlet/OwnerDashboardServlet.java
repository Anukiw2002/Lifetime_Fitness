package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.SessionUtils  ;

import java.io.IOException;


public class OwnerDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(req, resp, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }

        req.getRequestDispatcher("/WEB-INF/views/owner/owner-dashboard.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/owner/memberManagement.jsp").forward(req, resp);
    }
}
