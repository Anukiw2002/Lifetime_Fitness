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

        // Ensure correct encoding
        request.setCharacterEncoding("UTF-8");

        // Validate session
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

            // Get the number of sets for this exercise
            int totalSets = Integer.parseInt(request.getParameter("totalSets"));

            // Save workout logs to database
            WorkoutLogsDAO workoutLogsDAO = new WorkoutLogsDAO();
            boolean insertSuccess = false;
            String userNotes = request.getParameter("userNotes");

            // Loop through all sets and insert logs for each
            for (int set = 1; set <= totalSets; set++) {
                String weightParam = request.getParameter("weight" + set);
                String repsParam = request.getParameter("reps" + set);

                // Skip if both weight and reps are empty
                if ((weightParam == null || weightParam.isEmpty()) &&
                        (repsParam == null || repsParam.isEmpty())) {
                    continue;
                }

                Double weight = null;
                int reps = 0;

                // Parse weight if provided
                if (weightParam != null && !weightParam.isEmpty()) {
                    try {
                        weight = Double.parseDouble(weightParam);
                    } catch (NumberFormatException e) {
                        // Log or handle invalid weight
                        continue;
                    }
                }

                // Parse reps if provided
                if (repsParam != null && !repsParam.isEmpty()) {
                    try {
                        reps = Integer.parseInt(repsParam);
                    } catch (NumberFormatException e) {
                        // Log or handle invalid reps
                        continue;
                    }
                }

                // Insert log for this set
                boolean setInsertSuccess = workoutLogsDAO.insertWorkoutLogs(
                        userId, workoutId, exerciseId, set, weight, reps, userNotes
                );

                // Track overall success
                insertSuccess |= setInsertSuccess;
            }

            // Handle navigation based on button pressed
            if (action != null) {
                switch (action) {
                    case "previous":
                        // Navigate to previous exercise
                        response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex - 1));
                        break;
                    case "next":
                        // Navigate to next exercise
                        response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
                        break;
                    case "finish":
                        // Finish workout and go to stats page
                        response.sendRedirect(request.getContextPath() + "/WorkoutStats?workoutId=" + workoutId);
                        break;
                    default:
                        // Default navigation if no action specified
                        response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
                }
            } else {
                // Log success/failure message
                if (insertSuccess) {
                    request.setAttribute("successMessage", "Workout logs saved successfully");
                } else {
                    request.setAttribute("errorMessage", "No workout logs were saved");
                }

                // Default navigation if no action
                response.sendRedirect(request.getContextPath() + "/StartExercises?workoutId=" + workoutId + "&exerciseIndex=" + (exerciseIndex + 1));
            }

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