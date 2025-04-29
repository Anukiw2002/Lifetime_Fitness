package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.BookSessionDAO;
import org.example.demo2.dao.BookingConstraintsDAO;
import org.example.demo2.model.BookSession;
import org.example.demo2.model.BookingConstraints;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@WebServlet("/bookSessionConfirmation")
public class BookSessionConfirmationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Get booking constraints for max weeks
        BookingConstraintsDAO constraintsDAO = new BookingConstraintsDAO();
        BookingConstraints constraints = constraintsDAO.getLatestConstraints();
        int maxAdvanceWeeks = constraints != null ? constraints.getMaxBookingAdvanceWeeks() : 2;

        // Calculate the maximum end date
        Calendar maxCal = Calendar.getInstance();
        maxCal.add(Calendar.WEEK_OF_YEAR, maxAdvanceWeeks);
        Date maxEndDate = new Date(maxCal.getTimeInMillis());

        // Format for display
        LocalDate maxLocalDate = maxEndDate.toLocalDate();
        String maxEndDateFormatted = maxLocalDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

        // Calculate reasonable max occurrences (e.g., max days * 2 for safety)
        int maxOccurrences = maxAdvanceWeeks * 14; // 2 weeks = 14 days

        // For date inputs
        String maxEndDateISO = maxLocalDate.toString(); // yyyy-MM-dd format

        request.setAttribute("maxAdvanceWeeks", maxAdvanceWeeks);
        request.setAttribute("maxEndDate", maxEndDateISO);
        request.setAttribute("maxEndDateFormatted", maxEndDateFormatted);
        request.setAttribute("maxOccurrences", maxOccurrences);

        request.getRequestDispatcher("/WEB-INF/views/client/bookSessionConfirmation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try {
            String selectedDateStr = request.getParameter("selectedDate");
            String selectedTimeStr = request.getParameter("selectedSlot");
            String status = "booked";
            int userId = (int) session.getAttribute("userId");
            String frequency = request.getParameter("frequency");

            String timeOnly = selectedTimeStr.split(" - ")[0];
            System.out.println("Time extracted: " + timeOnly);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = LocalTime.parse(timeOnly, inputFormatter).format(outputFormatter);
            System.out.println("Formatted time: " + formattedTime);

            Date date = Date.valueOf(selectedDateStr);
            Time timeSlot = Time.valueOf(formattedTime);

            BookSessionDAO bookSessionDAO = new BookSessionDAO();
            boolean insertSuccess = false;

            if ("custom".equals(frequency)) {
                // Handle custom recurrence
                String[] selectedDays = request.getParameterValues("weekday");
                String endRecurrenceType = request.getParameter("endRecurrenceType");

                List<String> daysList = new ArrayList<>();
                if (selectedDays != null) {
                    daysList = Arrays.asList(selectedDays);
                } else {
                    // If no days selected, default to the day of the selected date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    daysList = Arrays.asList(getDayName(dayOfWeek));
                }

                Date endDate = null;
                Integer occurrences = null;

                if ("custom-date".equals(endRecurrenceType)) {
                    String endDateStr = request.getParameter("endDate");
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        endDate = Date.valueOf(endDateStr);
                    }
                } else if ("occurrences".equals(endRecurrenceType)) {
                    String occurrencesStr = request.getParameter("occurrences");
                    if (occurrencesStr != null && !occurrencesStr.isEmpty()) {
                        occurrences = Integer.parseInt(occurrencesStr);
                    }
                }

                insertSuccess = bookSessionDAO.createCustomRecurringBooking(
                        date, timeSlot, status, userId, daysList, endDate, occurrences);
            } else {
                // Use existing method for simple recurrence
                insertSuccess = bookSessionDAO.createRecurringSessionBooking(
                        date, timeSlot, status, userId, frequency);
            }

            if (insertSuccess) {
                request.setAttribute("successMessage", "Booking confirmed successfully!");
                response.sendRedirect(request.getContextPath() + "/clientBookings");
            } else {
                request.setAttribute("errorMessage", "Booking failed");
                request.getRequestDispatcher("/bookSession").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing time: " + e.getMessage());
            request.setAttribute("errorMessage", "Invalid booking details: " + e.getMessage());
            request.getRequestDispatcher("/bookSession").forward(request, response);
        }
    }

    // Add the helper method to convert Calendar day constant to string
    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY: return "MONDAY";
            case Calendar.TUESDAY: return "TUESDAY";
            case Calendar.WEDNESDAY: return "WEDNESDAY";
            case Calendar.THURSDAY: return "THURSDAY";
            case Calendar.FRIDAY: return "FRIDAY";
            case Calendar.SATURDAY: return "SATURDAY";
            case Calendar.SUNDAY: return "SUNDAY";
            default: return "";
        }
    }

    private String getFormattedMaxEndDate(int maxWeeks) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, maxWeeks);

        LocalDate maxDate = LocalDate.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
        return maxDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
    }


}

