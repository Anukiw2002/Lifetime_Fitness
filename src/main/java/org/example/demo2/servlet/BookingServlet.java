package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/booking/*")

public class BookingServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String step = request.getPathInfo();

        switch (step) {
            case "/owner":
                request.getRequestDispatcher("/WEB-INF/views/owner/viewBookings.jsp").forward(request, response);
                break;
            case "/constraints":
                request.getRequestDispatcher("/WEB-INF/views/owner/bookingConstraints.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("/instructor-onboarding/step1");
                break;
        }
    }
}
