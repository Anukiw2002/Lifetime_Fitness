package org.example.demo2.servlet;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/rescheduleSession")
public class RescheduleBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Get the bookingId from the request
        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/clientBookings");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdParam);

            // Store bookingId in session for use in the form
            session.setAttribute("bookingId", bookingId);

            // Retrieve userId from the session
            int userId = (int) session.getAttribute("userId");
            request.setAttribute("userId", userId);

            // Get start and end times from the database and set them in the session
            BookSessionDAO bookSessionDAO = new BookSessionDAO();
            bookSessionDAO.getStartEndTime(userId, session);

            // Get the original booking details
            BookSession originalBooking = bookSessionDAO.getBookingById(bookingId);
            if (originalBooking != null) {
                // Store the original booking details in the session
                session.setAttribute("originalDate", originalBooking.getDate());
                session.setAttribute("originalTime", originalBooking.getTimeSlot());
            }

            // Forward the request to rescheduleSession.jsp
            request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/clientBookings");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // First check if this is an AJAX request for date selection
        // First check if this is an AJAX request for date selection
        String selectedDate = request.getParameter("selectedDate");
        if (selectedDate != null && !selectedDate.isEmpty()) {
            // This is an AJAX request for time slots
            String startTimeStr = (String) session.getAttribute("startTime");
            String endTimeStr = (String) session.getAttribute("endTime");
            Date originalDate = (Date) session.getAttribute("originalDate");
            Time originalTime = (Time) session.getAttribute("originalTime");

            if (startTimeStr != null && endTimeStr != null) {
                try {
                    LocalTime start = LocalTime.parse(startTimeStr);
                    LocalTime end = LocalTime.parse(endTimeStr);

                    // If the selected date is today, update start time to current time if it's later
                    LocalDate today = LocalDate.now();
                    LocalDate selected = LocalDate.parse(selectedDate);

                    if (selected.equals(today)) {
                        LocalTime now = LocalTime.now();
                        if (now.isAfter(start)) {
                            // Round up to the next full hour
                            start = now.plusMinutes(59).withMinute(0).withSecond(0).withNano(0);
                            if (start.isAfter(end)) {
                                response.getWriter().write("<p>No available slots for the rest of the day.</p>");
                                return;
                            }
                        }
                    }

                    StringBuilder timeSlotsHtml = new StringBuilder();
                    LocalTime currentSlot = start;

                    while (!currentSlot.isAfter(end.minusHours(1))) {
                        // Create 1-hour slot
                        LocalTime slotEnd = currentSlot.plusHours(1);

                        // Ensure the slot does not go beyond the actual end time
                        if (slotEnd.isAfter(end)) {
                            slotEnd = end;
                        }

                        // Check if this time slot is the same as the original booking
                        boolean isOriginalTimeSlot = false;
                        if (originalDate != null && originalTime != null) {
                            LocalDate originalLocalDate = originalDate.toLocalDate();
                            LocalTime originalLocalTime = originalTime.toLocalTime();

                            if (selected.equals(originalLocalDate) &&
                                    currentSlot.equals(originalLocalTime)) {
                                isOriginalTimeSlot = true;
                            }
                        }

                        // Only display this slot if it's not the original time slot
                        if (!isOriginalTimeSlot) {
                            // Format the time slot for display
                            String slotDisplay = currentSlot.format(DateTimeFormatter.ofPattern("h:mm a")) + " - "
                                    + slotEnd.format(DateTimeFormatter.ofPattern("h:mm a"));

                            // Append the slot to the HTML list
                            timeSlotsHtml.append("<li style='cursor:pointer;' onClick='selectSlot(\"")
                                    .append(selectedDate)
                                    .append("\", \"")
                                    .append(currentSlot)
                                    .append("\", \"")
                                    .append(slotDisplay)
                                    .append("\")'>")
                                    .append(slotDisplay)
                                    .append("</li>");
                        }

                        // Move to the next slot (1 hour duration)
                        currentSlot = currentSlot.plusHours(1);
                    }

                    // Handle the last slot (if any) that is less than 1 hour
                    if (!currentSlot.equals(end)) {
                        // Check if this time slot is the same as the original booking
                        boolean isOriginalTimeSlot = false;
                        if (originalDate != null && originalTime != null) {
                            LocalDate originalLocalDate = originalDate.toLocalDate();
                            LocalTime originalLocalTime = originalTime.toLocalTime();

                            if (selected.equals(originalLocalDate) &&
                                    currentSlot.equals(originalLocalTime)) {
                                isOriginalTimeSlot = true;
                            }
                        }

                        if (!isOriginalTimeSlot) {
                            String lastSlotDisplay = currentSlot.format(DateTimeFormatter.ofPattern("h:mm a")) + " - "
                                    + end.format(DateTimeFormatter.ofPattern("h:mm a"));

                            timeSlotsHtml.append("<li style='cursor:pointer;' onClick='selectSlot(\"")
                                    .append(selectedDate)
                                    .append("\", \"")
                                    .append(currentSlot)
                                    .append("\", \"")
                                    .append(lastSlotDisplay)
                                    .append("\")'>")
                                    .append(lastSlotDisplay)
                                    .append("</li>");
                        }
                    }

                    // Check if any time slots were added
                    if (timeSlotsHtml.length() == 0) {
                        if (selected.equals(originalDate.toLocalDate())) {
                            response.getWriter().write("<p>Your session is already booked for this date. Please select a different date or time.</p>");
                        } else {
                            response.getWriter().write("<p>No available time slots for this date.</p>");
                        }
                    } else {
                        response.setContentType("text/html");
                        response.getWriter().write("<ul>" + timeSlotsHtml.toString() + "</ul>");
                    }

                } catch (DateTimeParseException e) {
                    response.getWriter().write("<p>Error: Invalid start or end time format.</p>");
                }
            } else {
                response.getWriter().write("<p>Error: Start and end times are not set.</p>");
            }
            return;
        }

        // If not an AJAX request, this is the form submission for rescheduling
        String bookingIdParam = request.getParameter("bookingId");
        String newDateParam = request.getParameter("newDate");
        String newTimeParam = request.getParameter("newTime");
        String newTimeDisplayParam = request.getParameter("newTimeDisplay");

        if (bookingIdParam != null && newDateParam != null && newTimeParam != null) {
            try {
                int bookingId = Integer.parseInt(bookingIdParam);

                // Convert the date string to a SQL Date
                Date sqlDate = Date.valueOf(newDateParam);

                // Convert the time string to a SQL Time
                Time sqlTime = Time.valueOf(LocalTime.parse(newTimeParam));

                // Call the DAO to reschedule the session
                BookSessionDAO bookSessionDAO = new BookSessionDAO();
                boolean success = bookSessionDAO.rescheduleSession(sqlDate, sqlTime, bookingId);

                if (success) {
                    // Set a success message in the session
                    session.setAttribute("message", "Session rescheduled successfully!");
                    response.sendRedirect(request.getContextPath() + "/clientBookings");
                } else {
                    // Set an error message
                    request.setAttribute("error", "Failed to reschedule session.");
                    request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
                }

            } catch ( IllegalArgumentException e) {
                request.setAttribute("error", "Invalid input. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Missing required parameters.");
            request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
        }
    }
}