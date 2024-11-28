package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/InstructorViewNotification")
public class InstructorNotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        // Forward the request to navbar.html
        request.getRequestDispatcher("/WEB-INF/views/instructor/instructorNotifications.jsp").forward(request, response);
    }
}
