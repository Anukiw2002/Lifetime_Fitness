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
            int user_id = (int) session.getAttribute("userId");
            int workout_id = (int) session.getAttribute("workoutId");
            int exercise_id = (int) session.getAttribute("exerciseId");

            // Get the number of sets for this exercise
            int totalSets = Integer.parseInt(request.getParameter("totalSets"));

            WorkoutLogsDAO workoutLogsDAO = new WorkoutLogsDAO();
            boolean insertSuccess = false;

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

                // Get user notes (optional)
                String notes = request.getParameter("userNotes");

                // Insert log for this set
                boolean setInsertSuccess = workoutLogsDAO.insertWorkoutLogs(
                        user_id, workout_id, exercise_id, set, weight, reps, notes
                );

                // Track overall success
                insertSuccess |= setInsertSuccess;
            }

            if (insertSuccess) {
                response.sendRedirect("GetAllServlet?status=insertSuccess");
            } else {
                request.setAttribute("errorMessage", "No workout logs were inserted");
                RequestDispatcher dis = request.getRequestDispatcher("wrong.jsp");
                dis.forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing workout logs");
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