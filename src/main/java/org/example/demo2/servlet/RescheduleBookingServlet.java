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


        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }


        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/clientBookings");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdParam);

            session.setAttribute("bookingId", bookingId);

            int userId = (int) session.getAttribute("userId");
            request.setAttribute("userId", userId);

            BookSessionDAO bookSessionDAO = new BookSessionDAO();
            bookSessionDAO.getStartEndTime(userId, session);


            BookSession originalBooking = bookSessionDAO.getBookingById(bookingId);
            if (originalBooking != null) {

                session.setAttribute("originalDate", originalBooking.getDate());
                session.setAttribute("originalTime", originalBooking.getTimeSlot());
            }

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


        String selectedDate = request.getParameter("selectedDate");
        if (selectedDate != null && !selectedDate.isEmpty()) {
            String startTimeStr = (String) session.getAttribute("startTime");
            String endTimeStr = (String) session.getAttribute("endTime");
            Date originalDate = (Date) session.getAttribute("originalDate");
            Time originalTime = (Time) session.getAttribute("originalTime");
            int userId = (int) session.getAttribute("userId");

            if (startTimeStr != null && endTimeStr != null) {
                try {
                    LocalTime start = LocalTime.parse(startTimeStr);
                    LocalTime end = LocalTime.parse(endTimeStr);
                    BookSessionDAO bookSessionDAO = new BookSessionDAO();

                    LocalDate today = LocalDate.now();
                    LocalDate selected = LocalDate.parse(selectedDate);

                    if (selected.equals(today)) {
                        LocalTime now = LocalTime.now();
                        if (now.isAfter(start)) {

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
                        LocalTime slotEnd = currentSlot.plusHours(1);

                        if (slotEnd.isAfter(end)) {
                            slotEnd = end;
                        }

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

                            String slotDisplay = currentSlot.format(DateTimeFormatter.ofPattern("h:mm a")) + " - "
                                    + slotEnd.format(DateTimeFormatter.ofPattern("h:mm a"));

                            java.sql.Date sqlDate = java.sql.Date.valueOf(selected);
                            java.sql.Time sqlTime = java.sql.Time.valueOf(currentSlot);

                            String availabilityStatus = bookSessionDAO.getSessionAvailabilityLabel(sqlDate, sqlTime);


                            boolean userHasBooked = bookSessionDAO.hasUserBookedSlot(userId, sqlDate, sqlTime);

                            String displayStatus = availabilityStatus;
                            if (userHasBooked) {
                                displayStatus = "Already Booked";
                            }

                            timeSlotsHtml.append("<li data-availability=\"")
                                    .append(displayStatus)
                                    .append("\" style='cursor:")
                                    .append(("Fully Booked".equals(availabilityStatus) || userHasBooked) ? "not-allowed" : "pointer")
                                    .append(";' ");


                            if (!"Fully Booked".equals(availabilityStatus) && !userHasBooked) {
                                timeSlotsHtml.append("onClick='selectSlot(\"")
                                        .append(selectedDate)
                                        .append("\", \"")
                                        .append(currentSlot)
                                        .append("\", \"")
                                        .append(slotDisplay)
                                        .append("\")'");
                            } else if (userHasBooked) {
                                timeSlotsHtml.append("onClick='alreadyBookedAlert()'");
                            }

                            timeSlotsHtml.append(">")
                                    .append(slotDisplay)
                                    .append("</li>");
                        }

                        currentSlot = currentSlot.plusHours(1);
                    }


                    if (!currentSlot.equals(end)) {

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

                            java.sql.Date sqlDate = java.sql.Date.valueOf(selected);
                            java.sql.Time sqlTime = java.sql.Time.valueOf(currentSlot);


                            String availabilityStatus = bookSessionDAO.getSessionAvailabilityLabel(sqlDate, sqlTime);


                            boolean userHasBooked = bookSessionDAO.hasUserBookedSlot(userId, sqlDate, sqlTime);


                            String displayStatus = availabilityStatus;
                            if (userHasBooked) {
                                displayStatus = "Already Booked";
                            }

                            timeSlotsHtml.append("<li data-availability=\"")
                                    .append(displayStatus)
                                    .append("\" style='cursor:")
                                    .append(("Fully Booked".equals(availabilityStatus) || userHasBooked) ? "not-allowed" : "pointer")
                                    .append(";' ");


                            if (!"Fully Booked".equals(availabilityStatus) && !userHasBooked) {
                                timeSlotsHtml.append("onClick='selectSlot(\"")
                                        .append(selectedDate)
                                        .append("\", \"")
                                        .append(currentSlot)
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
                    }

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


        String bookingIdParam = request.getParameter("bookingId");
        String newDateParam = request.getParameter("newDate");
        String newTimeParam = request.getParameter("newTime");
        String newTimeDisplayParam = request.getParameter("newTimeDisplay");

        if (bookingIdParam != null && newDateParam != null && newTimeParam != null) {
            try {
                int bookingId = Integer.parseInt(bookingIdParam);

                Date sqlDate = Date.valueOf(newDateParam);

                Time sqlTime = Time.valueOf(LocalTime.parse(newTimeParam));

                BookSessionDAO bookSessionDAO = new BookSessionDAO();
                boolean success = bookSessionDAO.rescheduleSession(sqlDate, sqlTime, bookingId);

                if (success) {
                    session.setAttribute("message", "Session rescheduled successfully!");
                    response.sendRedirect(request.getContextPath() + "/clientBookings");
                } else {

                    request.setAttribute("error", "Failed to reschedule session.");
                    request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
                }

            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid input. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Missing required parameters.");
            request.getRequestDispatcher("/WEB-INF/views/client/rescheduleBooking.jsp").forward(request, response);
        }
    }
}