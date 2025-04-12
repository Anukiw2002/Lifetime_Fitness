package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.WorkoutLogsDAO;

import java.io.IOException;

@WebServlet("/InsertWorkoutLogsServlet")
public class InsertWorkoutLogsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        request.setCharacterEncoding("UTF-8");

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            int workoutId = Integer.parseInt(request.getParameter("workoutId"));
            int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
            int exerciseIndex = Integer.parseInt(request.getParameter("exerciseIndex"));
            String action = request.getParameter("action");
            int totalSets = Integer.parseInt(request.getParameter("totalSets"));
            String userNotes = request.getParameter("userNotes");

            WorkoutLogsDAO dao = new WorkoutLogsDAO();

            // Check if session_id already exists for this session
            Integer sessionId = (Integer) session.getAttribute("currentWorkoutSessionId");

            if (sessionId == null) {
                // Create a new workout session if it doesn't exist
                sessionId = dao.createWorkoutSession(userId, workoutId);
                session.setAttribute("currentWorkoutSessionId", sessionId);
            }

            boolean insertSuccess = false;

            for (int set = 1; set <= totalSets; set++) {
                String weightParam = request.getParameter("weight" + set);
                String repsParam = request.getParameter("reps" + set);

                if ((weightParam == null || weightParam.isEmpty()) && (repsParam == null || repsParam.isEmpty())) {
                    continue;
                }

                Double weight = null;
                int reps = 0;

                if (weightParam != null && !weightParam.isEmpty()) {
                    try {
                        weight = Double.parseDouble(weightParam);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }

                if (repsParam != null && !repsParam.isEmpty()) {
                    try {
                        reps = Integer.parseInt(repsParam);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }

                // Insert logs with sessionId, passing sessionId to the DAO method
                boolean setInserted = dao.insertWorkoutLogsWithSession(
                        sessionId, userId, workoutId, exerciseId, set, weight, reps, userNotes
                );
                insertSuccess |= setInserted;
            }

            // Handle navigation actions (previous, next, finish)
            if ("previous".equals(action)) {
                response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex - 1));
            } else if ("next".equals(action)) {
                response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
            } else if ("finish".equals(action)) {
                // Update the end time in the workout session
                boolean endTimeUpdated = dao.updateWorkoutEndTime(sessionId);

                if (endTimeUpdated) {
                    // Remove the session attribute since the workout is finished
                    session.removeAttribute("currentWorkoutSessionId");

                    // Redirect to the WorkoutStats page with necessary parameters
                    response.sendRedirect(request.getContextPath() + "/WorkoutStats?workoutId=" + workoutId + "&sessionId=" + sessionId);
                } else {
                    // If end time update fails, forward to an error page
                    request.setAttribute("errorMessage", "Error updating workout end time");
                    request.getRequestDispatcher("/WEB-INF/views/client/wrong.jsp").forward(request, response);
                }

            } else {
                response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing workout logs: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/wrong.jsp").forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/client/workoutLogs.jsp").forward(request, response);
    }
}
