package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.WorkoutLogsDAO;
import org.example.demo2.model.WorkoutStats;
import org.example.demo2.model.WorkoutSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/WorkoutStats")
public class WorkoutStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Get workoutId and sessionId from request
        String workoutIdParam = request.getParameter("workoutId");
        String sessionIdParam = request.getParameter("sessionId");

        if (workoutIdParam == null || workoutIdParam.isEmpty() || sessionIdParam == null || sessionIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing workoutId or sessionId");
            return;
        }

        int workoutId = Integer.parseInt(workoutIdParam);
        int sessionId = Integer.parseInt(sessionIdParam);

        WorkoutLogsDAO dao = new WorkoutLogsDAO();

        // Get stats for the session
        WorkoutStats stats = dao.getStatsForSession(userId, workoutId, sessionId);

        double avgWeight = 0;
        if (stats != null && stats.getTotalSets() > 0) {
            avgWeight = stats.getTotalWeight() / stats.getTotalSets();
        }

        // Get session details for duration display
        WorkoutSession sessionDetails = dao.getWorkoutSessionDetails(sessionId);
        if (sessionDetails == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Session not found");
            return;
        }

        // Calculate workout duration
        long duration = 0;
        if (sessionDetails.getStarted_at() != null && sessionDetails.getEnded_at() != null) {
            duration = sessionDetails.getEnded_at().getTime() - sessionDetails.getStarted_at().getTime();
        }

        long hours = duration / (1000 * 60 * 60);
        long minutes = (duration % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (duration % (1000 * 60)) / 1000;

        // Get personal bests
        List<Map<String, Object>> personalBests = dao.getPersonalBests(userId, workoutId, sessionId);

        // Set attributes for the view
        request.setAttribute("duration", String.format("%02d:%02d:%02d", hours, minutes, seconds));
        request.setAttribute("sessionDetails", sessionDetails);
        request.setAttribute("stats", stats);
        request.setAttribute("avgWeight", avgWeight);
        request.setAttribute("personalBests", personalBests);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/client/workoutStats.jsp");
        dispatcher.forward(request, response);
    }
}
