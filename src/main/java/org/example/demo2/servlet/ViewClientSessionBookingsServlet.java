package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.WorkoutLogsDAO;
import org.example.demo2.model.BookSession;
import org.example.demo2.dao.BookSessionDAO;
import org.example.demo2.model.WorkoutSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/clientBookings")
public class ViewClientSessionBookingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        BookSessionDAO dao = new BookSessionDAO();
        List<BookSession> sessionList = dao.getAllBookingsForClient(userId);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, d MMMM");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");

        List<Map<String, String>> formattedSessions = new ArrayList<>();

        for (BookSession slot : sessionList) {
            Map<String, String> formattedSession = new HashMap<>();

            formattedSession.put("bookingId", String.valueOf(slot.getBookingId()));

            // Format the date (this part is fine as is)
            formattedSession.put("formattedDate", dateFormatter.format(slot.getDate()));

            // Convert java.sql.Time to java.util.Date
            java.util.Date startTime = new java.util.Date(slot.getTimeSlot().getTime());

            // Add 1 hour
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            java.util.Date endTime = cal.getTime();

            // Format times
            formattedSession.put("startTime", timeFormatter.format(startTime));
            formattedSession.put("endTime", timeFormatter.format(endTime));

            formattedSessions.add(formattedSession);
        }

        request.setAttribute("formattedSessions", formattedSessions);

        WorkoutLogsDAO workoutLogsDAO = new WorkoutLogsDAO();
        List<WorkoutSession> workoutSessions = workoutLogsDAO.getCompletedSessions(userId);
        request.setAttribute("workoutSessions", workoutSessions);

        request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
    }
}