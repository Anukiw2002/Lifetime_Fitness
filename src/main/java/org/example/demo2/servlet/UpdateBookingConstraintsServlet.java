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
            return; // If not authorized, the redirection will be handled by the utility method
        }
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure correct encoding
        request.setCharacterEncoding("UTF-8");

        try {
            // Get the current constraint record first to get its ID
            BookingConstraintsDAO bookingConstraintDAO = new BookingConstraintsDAO();
            BookingConstraints currentConstraints = bookingConstraintDAO.getLatestConstraints();

            // If no constraints exist yet, we'll need to create one
            boolean isUpdate = (currentConstraints != null);
            int constraintId = isUpdate ? currentConstraints.getConstraintId() : 0;

            // Parse values with appropriate error handling
            int cancelLimitMinutes = 0;  // Default to 0 if disabled
            if (!"false".equals(request.getParameter("cancelEnabled"))) {
                cancelLimitMinutes = convertToMinutes(
                        request.getParameter("cancelLimitValue"),
                        request.getParameter("cancelLimitUnit")
                );
            }

            int rescheduleLimitMinutes = 0;  // Default to 0 if disabled
            if (!"false".equals(request.getParameter("rescheduleEnabled"))) {
                rescheduleLimitMinutes = convertToMinutes(
                        request.getParameter("rescheduleLimitValue"),
                        request.getParameter("rescheduleLimitUnit")
                );
            }

            int minBookingGapMins = convertToMinutes(
                    request.getParameter("minBookingGapValue"),
                    request.getParameter("minBookingGapUnit")
            );

            // Parse weeks value (no conversion needed for this field)
            int maxBookingAdvanceWeeks = Integer.parseInt(request.getParameter("maxBookingAdvanceValue"));
            if ("months".equals(request.getParameter("maxBookingAdvanceUnit"))) {
                // Convert months to weeks (approximate)
                maxBookingAdvanceWeeks = maxBookingAdvanceWeeks * 4;
            }

            // Parse boolean value
            boolean showBookingCount = "on".equals(request.getParameter("showBookingCount"));

            // Parse max bookings per slot
            int maxBookingsPerSlot = 50;  // Default value if disabled
            if (!"false".equals(request.getParameter("maxBookingsEnabled"))) {
                maxBookingsPerSlot = Integer.parseInt(request.getParameter("maxBookingsPerSlot"));
            }

            boolean success;

            // Update or insert based on whether constraints already exist
            if (isUpdate) {
                success = bookingConstraintDAO.updateConstraints(
                        constraintId,
                        cancelLimitMinutes,
                        rescheduleLimitMinutes,
                        minBookingGapMins,
                        maxBookingAdvanceWeeks,
                        showBookingCount,
                        maxBookingsPerSlot
                );
            } else {
                success = bookingConstraintDAO.insertConstraints(
                        cancelLimitMinutes,
                        rescheduleLimitMinutes,
                        minBookingGapMins,
                        maxBookingAdvanceWeeks,
                        showBookingCount,
                        maxBookingsPerSlot
                );
            }

            if (success) {
                // Redirect with status param
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

    // Converts a value to minutes based on the specified time unit
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
                return value; // Default to minutes if unknown unit
        }
    }
}