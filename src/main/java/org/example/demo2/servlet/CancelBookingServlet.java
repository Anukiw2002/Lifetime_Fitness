package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;

import java.io.IOException;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String bookingIdParam = request.getParameter("bookingId");

        if (bookingIdParam != null && !bookingIdParam.isEmpty()) {
            try {
                int bookingId = Integer.parseInt(bookingIdParam);
                BookSessionDAO bookSession = new BookSessionDAO();
                boolean success = bookSession.cancelSession(bookingId);

                if (success) {
                    // Redirect back to the session list or confirmation page
                    response.sendRedirect(request.getContextPath() + "/clientBookings");
                } else {
                    // Could not cancel (maybe invalid bookingId?)
                    request.setAttribute("error", "Could not cancel the session.");
                    request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID format.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing booking ID.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam != null && !bookingIdParam.isEmpty()) {
            try {
                int bookingId = Integer.parseInt(bookingIdParam);
                BookSessionDAO bookSession = new BookSessionDAO();
                boolean success = bookSession.cancelSession(bookingId);

                if (success) {
                    // Redirect back to the session list or confirmation page
                    response.sendRedirect(request.getContextPath() + "/clientBookings");
                } else {
                    // Could not cancel (maybe invalid bookingId?)
                    request.setAttribute("error", "Could not cancel the session.");
                    request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID format.");
            }
        } else {
            // If no bookingId provided, just display the page
            request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
        }
    }
}