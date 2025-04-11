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

import java.io.IOException;

@WebServlet("/WorkoutStats")
public class WorkoutStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Get user and workout IDs
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp"); // Or any login route
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String workoutIdParam = request.getParameter("workoutId");
        if (workoutIdParam == null || workoutIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing workoutId");
            return;
        }

        int workoutId = Integer.parseInt(workoutIdParam);

        // Step 2: Fetch stats from DAO
        WorkoutLogsDAO dao = new WorkoutLogsDAO();
        WorkoutStats stats = dao.getStats(userId, workoutId);

        // Step 3: Calculate average weight per set
        double avgWeight = 0;
        if (stats != null && stats.getTotalSets() > 0) {
            avgWeight = stats.getTotalWeight() / stats.getTotalSets();
        }

        // Step 4: Set attributes for the frontend (JSP)
        request.setAttribute("stats", stats);
        request.setAttribute("avgWeight", avgWeight);

        // Step 5: Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/client/workoutStats.jsp");
        dispatcher.forward(request, response);

        System.out.println("Stats received: " + stats.getTotalWeight() + ", " + stats.getTotalReps() + ", " + stats.getTotalSets());

    }


}
