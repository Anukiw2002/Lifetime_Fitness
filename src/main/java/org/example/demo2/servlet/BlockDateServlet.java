package org.example.demo2.servlet;

import org.example.demo2.dao.BookingConstraintsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@WebServlet("/booking/blockDate")
public class BlockDateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        BookingConstraintsDAO dao = new BookingConstraintsDAO();

        try {
            LocalDate blockDate = LocalDate.parse(request.getParameter("blockDate"));
            boolean isFullDay = request.getParameter("isFullDay") != null;

            LocalTime startTime = null;
            LocalTime endTime = null;

            if (!isFullDay) {
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");

                if (start == null || start.isEmpty() || end == null || end.isEmpty()) {
                    throw new IllegalArgumentException("Time fields required for partial-day blocks");
                }

                startTime = LocalTime.parse(start);
                endTime = LocalTime.parse(end);

                if (startTime.isAfter(endTime)) {
                    throw new IllegalArgumentException("Start time must be before end time");
                }
            }

            String reason = request.getParameter("reason");
            if (reason == null || reason.trim().isEmpty()) {
                throw new IllegalArgumentException("Reason is required");
            }

            LocalDate today = LocalDate.now();
            if (blockDate.equals(today) && !isFullDay) {
                LocalTime now = LocalTime.now();
                if (startTime.isBefore(now)) {
                    throw new IllegalArgumentException("Start time must be after the current time for today.");
                }
            }

            boolean success = dao.addBlockedDates(blockDate, startTime, endTime, isFullDay, reason);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/booking/viewBlockedDate");
            } else {
                response.sendRedirect(request.getContextPath() + "/booking/constraints?error=blockFailed");
            }

        } catch (DateTimeParseException e) {
            response.sendRedirect(request.getContextPath() + "/booking/constraints?error=dateTimeFormat");
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/booking/constraints?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/booking/constraints?error=unknownError");
        }
    }
}