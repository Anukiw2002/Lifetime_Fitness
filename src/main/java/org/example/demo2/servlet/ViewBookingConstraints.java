package org.example.demo2.servlet;

import org.example.demo2.dao.BookingConstraintsDAO;
import org.example.demo2.model.BookingConstraints;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/booking/constraints")
public class ViewBookingConstraints extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get current constraints from the database
        BookingConstraintsDAO dao = new BookingConstraintsDAO();
        BookingConstraints constraints = dao.getLatestConstraints();

        // If constraints exist, add them to the request
        if (constraints != null) {
            request.setAttribute("constraints", constraints);
        }

        // Forward to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/owner/bookingConstraints.jsp");
        dispatcher.forward(request, response);
    }
}