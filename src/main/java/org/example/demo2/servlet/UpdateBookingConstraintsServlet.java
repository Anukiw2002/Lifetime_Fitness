package org.example.demo2.servlet;

import org.example.demo2.dao.BookingConstraintsDAO;
import org.example.demo2.model.BookingConstraints;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/booking/updateConstraints")
public class UpdateBookingConstraintsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        BookingConstraintsDAO dao = new BookingConstraintsDAO();
        BookingConstraints constraints = dao.getLatestConstraints();

        if (constraints != null) {
            request.setAttribute("constraints", constraints);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/owner/bookingConstraints.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            BookingConstraintsDAO bookingConstraintDAO = new BookingConstraintsDAO();
            BookingConstraints currentConstraints = bookingConstraintDAO.getLatestConstraints();

            boolean isUpdate = (currentConstraints != null);
            int constraintId = isUpdate ? currentConstraints.getConstraintId() : 0;

            int maxBookingAdvanceWeeks = Integer.parseInt(request.getParameter("maxBookingAdvanceValue"));
            if ("months".equals(request.getParameter("maxBookingAdvanceUnit"))) {

                maxBookingAdvanceWeeks = maxBookingAdvanceWeeks * 4;
            }


            int maxBookingsPerSlot = 50;
            if (!"false".equals(request.getParameter("maxBookingsEnabled"))) {
                maxBookingsPerSlot = Integer.parseInt(request.getParameter("maxBookingsPerSlot"));
            }

            boolean success;


            if (isUpdate) {
                success = bookingConstraintDAO.updateConstraints(
                        constraintId,
                        maxBookingAdvanceWeeks,
                        maxBookingsPerSlot
                );
            } else {
                success = bookingConstraintDAO.insertConstraints(
                        maxBookingAdvanceWeeks,
                        maxBookingsPerSlot
                );
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/booking/constraints?status=updateSuccess");
            } else {
                request.setAttribute("errorMessage", "Failed to save booking constraints");
                RequestDispatcher dis = request.getRequestDispatcher("/error.jsp");
                dis.forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            RequestDispatcher dis = request.getRequestDispatcher("/error.jsp");
            dis.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            RequestDispatcher dis = request.getRequestDispatcher("/error.jsp");
            dis.forward(request, response);
        }
    }

    private int convertToMinutes(String valueStr, String unit) {
        int value = Integer.parseInt(valueStr);

        switch (unit.toLowerCase()) {
            case "minutes":
                return value;
            case "hours":
                return value * 60;
            case "days":
                return value * 24 * 60;
            default:
                return value;
        }
    }
}