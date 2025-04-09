package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.BookSessionDAO;
import org.example.demo2.model.BookSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/bookSessionConfirmation")
public class BookSessionConfirmationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/client/bookSessionConfirmation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try {
            String selectedDateStr = request.getParameter("selectedDate");
            String selectedTimeStr = request.getParameter("selectedSlot");
            String status = "booked";
            int userId = (int) session.getAttribute("userId");// Get userId from session instead
            String frequency = request.getParameter("frequency");

            String timeOnly = selectedTimeStr.split(" - ")[0];

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = LocalTime.parse(timeOnly, inputFormatter).format(outputFormatter);

            Date date = Date.valueOf(selectedDateStr);
            Time timeSlot = Time.valueOf(formattedTime);

            // Use BookSessionDAO instead of BookSession
            BookSessionDAO bookSessionDAO = new BookSessionDAO();
            boolean insertSuccess = bookSessionDAO.createRecurringSessionBooking(date, timeSlot, status, userId, frequency);

            if (insertSuccess) {
                request.setAttribute("successMessage", "Booking confirmed successfully!");
                response.sendRedirect(request.getContextPath() + "/bookSession");
            } else {
                request.setAttribute("errorMessage", "Booking failed");
                request.getRequestDispatcher("/bookSession").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid booking details: " + e.getMessage());
            request.getRequestDispatcher("/bookSession").forward(request, response);
        }
    }


}


