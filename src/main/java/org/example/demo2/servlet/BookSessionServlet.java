package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.BookSessionDAO;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;

@WebServlet("/bookSession")
public class BookSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Retrieve userId from the session and set it as a request attribute
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        // Get start and end times from the database and set them in the session
        BookSessionDAO bookSessionDAO = new BookSessionDAO();
        bookSessionDAO.getStartEndTime(userId, session);

        // Forward the request to bookSession.jsp
        request.getRequestDispatcher("/WEB-INF/views/client/bookSession.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedDate = request.getParameter("selectedDate");
        HttpSession session = request.getSession();

        String startTimeStr = (String) session.getAttribute("startTime");
        String endTimeStr = (String) session.getAttribute("endTime");
        int userId = (int) session.getAttribute("userId");

        if (startTimeStr != null && endTimeStr != null) {
            try {
                LocalTime start = LocalTime.parse(startTimeStr);
                LocalTime end = LocalTime.parse(endTimeStr);
                BookSessionDAO bookSessionDAO = new BookSessionDAO();

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

                    // Format the time slot for display
                    String slotDisplay = currentSlot.format(DateTimeFormatter.ofPattern("h:mm a")) + " - "
                            + slotEnd.format(DateTimeFormatter.ofPattern("h:mm a"));

                    // Convert LocalTime to java.sql.Time for checking availability
                    java.sql.Date sqlDate = java.sql.Date.valueOf(selected);
                    java.sql.Time sqlTime = java.sql.Time.valueOf(currentSlot);

                    // Get availability status
                    String availabilityStatus = bookSessionDAO.getSessionAvailabilityLabel(sqlDate, sqlTime);

                    // Check if the user has already booked this slot
                    boolean userHasBooked = bookSessionDAO.hasUserBookedSlot(userId, sqlDate, sqlTime);

                    // Set status for already booked slots by this user
                    String displayStatus = availabilityStatus;
                    if (userHasBooked) {
                        displayStatus = "Already Booked";
                    }

                    // Append the slot to the HTML list with availability class
                    timeSlotsHtml.append("<li data-availability=\"")
                            .append(displayStatus)
                            .append("\" style='cursor:")
                            .append(("Fully Booked".equals(availabilityStatus) || userHasBooked) ? "not-allowed" : "pointer")
                            .append(";' ");

                    // Only add onClick handler if not fully booked and not already booked by user
                    if (!"Fully Booked".equals(availabilityStatus) && !userHasBooked) {
                        timeSlotsHtml.append("onClick='selectSlot(\"")
                                .append(selectedDate)
                                .append("\", \"")
                                .append(slotDisplay)
                                .append("\")'");
                    } else if (userHasBooked) {
                        timeSlotsHtml.append("onClick='alreadyBookedAlert()'");
                    }

                    timeSlotsHtml.append(">")
                            .append(slotDisplay)
                            .append("</li>");

                    // Move to the next slot (1 hour duration)
                    currentSlot = currentSlot.plusHours(1);
                }

                // Handle the last slot (if any) that is less than 1 hour
                if (!currentSlot.equals(end)) {
                    String lastSlotDisplay = currentSlot.format(DateTimeFormatter.ofPattern("h:mm a")) + " - "
                            + end.format(DateTimeFormatter.ofPattern("h:mm a"));

                    // Convert LocalTime to java.sql.Time for checking availability
                    java.sql.Date sqlDate = java.sql.Date.valueOf(selected);
                    java.sql.Time sqlTime = java.sql.Time.valueOf(currentSlot);

                    // Get availability status
                    String availabilityStatus = bookSessionDAO.getSessionAvailabilityLabel(sqlDate, sqlTime);

                    // Check if the user has already booked this slot
                    boolean userHasBooked = bookSessionDAO.hasUserBookedSlot(userId, sqlDate, sqlTime);

                    // Set status for already booked slots by this user
                    String displayStatus = availabilityStatus;
                    if (userHasBooked) {
                        displayStatus = "Already Booked";
                    }

                    timeSlotsHtml.append("<li data-availability=\"")
                            .append(displayStatus)
                            .append("\" style='cursor:")
                            .append(("Fully Booked".equals(availabilityStatus) || userHasBooked) ? "not-allowed" : "pointer")
                            .append(";' ");

                    // Only add onClick handler if not fully booked and not already booked by user
                    if (!"Fully Booked".equals(availabilityStatus) && !userHasBooked) {
                        timeSlotsHtml.append("onClick='selectSlot(\"")
                                .append(selectedDate)
                                .append("\", \"")
                                .append(lastSlotDisplay)
                                .append("\")'");
                    } else if (userHasBooked) {
                        timeSlotsHtml.append("onClick='alreadyBookedAlert()'");
                    }

                    timeSlotsHtml.append(">")
                            .append(lastSlotDisplay)
                            .append("</li>");
                }

                response.setContentType("text/html");
                response.getWriter().write("<ul>" + timeSlotsHtml.toString() + "</ul>");

            } catch (DateTimeParseException e) {
                response.getWriter().write("<p>Error: Invalid start or end time format.</p>");
            }
        } else {
            response.getWriter().write("<p>Error: Start and end times are not set.</p>");
        }
    }

}

