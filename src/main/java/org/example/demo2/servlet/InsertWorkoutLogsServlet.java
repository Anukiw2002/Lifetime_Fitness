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

            WorkoutLogsDAO workoutLogsDAO = new WorkoutLogsDAO();

            // Get or create session_id for this workout session
            Integer sessionId = (Integer) session.getAttribute("currentWorkoutSessionId");

            if (sessionId == null) {
                // New session: insert into workout_sessions and store sessionId
                sessionId = workoutLogsDAO.createWorkoutSession(userId, workoutId);
                session.setAttribute("currentWorkoutSessionId", sessionId);
            }

            boolean insertSuccess = false;

            for (int set = 1; set <= totalSets; set++) {
                String weightParam = request.getParameter("weight" + set);
                String repsParam = request.getParameter("reps" + set);

                if ((weightParam == null || weightParam.isEmpty()) &&
                        (repsParam == null || repsParam.isEmpty())) {
                    continue;
                }

                Double weight = null;
                int reps = 0;

                try {
                    if (weightParam != null && !weightParam.isEmpty()) {
                        weight = Double.parseDouble(weightParam);
                    }
                    if (repsParam != null && !repsParam.isEmpty()) {
                        reps = Integer.parseInt(repsParam);
                    }
                } catch (NumberFormatException e) {
                    continue; // skip this set if data is invalid
                }

                boolean setInsertSuccess = workoutLogsDAO.insertWorkoutLogsWithSession(
                        sessionId, userId, workoutId, exerciseId, set, weight, reps, userNotes
                );

                insertSuccess |= setInsertSuccess;
            }

            if (action != null) {
                switch (action) {
                    case "previous":
                        response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex - 1));
                        return;
                    case "next":
                        response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
                        return;
                    case "finish":
                        // Clear session after finishing workout
                        session.removeAttribute("currentWorkoutSessionId");
                        response.sendRedirect(request.getContextPath() + "/WorkoutStats?sessionId=" + sessionId);
                        return;
                }
            }

            if (insertSuccess) {
                request.setAttribute("successMessage", "Workout logs saved successfully");
            } else {
                request.setAttribute("errorMessage", "No workout logs were saved");
            }

            response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing workout logs: " + e.getMessage());
            RequestDispatcher dis = request.getRequestDispatcher("wrong.jsp");
            dis.forward(request, response);
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
