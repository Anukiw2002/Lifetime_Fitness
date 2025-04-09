package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ExerciseDAO;
import org.example.demo2.dao.WorkoutExerciseDAO;
import org.example.demo2.model.ClientWorkout;
import org.example.demo2.model.Exercise;
import org.example.demo2.model.WorkoutExercise;
import org.example.demo2.dao.ClientWorkoutDAO;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/StartExercises")
public class ClientStartWorkoutServlet extends HttpServlet {
    private ClientWorkoutDAO clientWorkoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;
    private ExerciseDAO exerciseDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        this.workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
        this.exerciseDAO = new ExerciseDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            // If the session is invalid or user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String workoutId = request.getParameter("workoutId");
        // Get the current exercise index if available
        String exerciseIndexParam = request.getParameter("exerciseIndex");
        int exerciseIndex = 0;

        if (exerciseIndexParam != null && !exerciseIndexParam.isEmpty()) {
            try {
                exerciseIndex = Integer.parseInt(exerciseIndexParam);
            } catch (NumberFormatException e) {
                // If index parsing fails, start from the first exercise
                exerciseIndex = 0;
            }
        }

        try {
            if (workoutId == null || workoutId.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/memberProfile");
                return;
            }

            // Load the workout with exercises
            ClientWorkout workout = clientWorkoutDAO.findWithExercises(Long.parseLong(workoutId));
            if (workout == null) {
                response.sendRedirect("clientWorkouts");
                return;
            }

            List<WorkoutExercise> exercises = workout.getExercises();

            // Ensure we have a valid exercise index
            if (exercises == null || exercises.isEmpty()) {
                // No exercises in this workout
                request.setAttribute("errorMessage", "This workout has no exercises.");
                response.sendRedirect(request.getContextPath() + "/clientWorkouts");
                return;
            }

            // Ensure the exercise index is within bounds
            if (exerciseIndex >= exercises.size()) {
                exerciseIndex = 0; // Reset to first exercise if out of bounds
            }

            // Get the current exercise
            WorkoutExercise currentExercise = exercises.get(exerciseIndex);

            // Set attributes for the JSP
            request.setAttribute("workout", workout);
            request.setAttribute("currentExercise", currentExercise);
            request.setAttribute("exerciseIndex", exerciseIndex);
            request.setAttribute("totalExercises", exercises.size());
            request.setAttribute("isFirstExercise", exerciseIndex == 0);
            request.setAttribute("isLastExercise", exerciseIndex == exercises.size() - 1);

            // Forward to the JSP
            request.getRequestDispatcher("/WEB-INF/views/client/workoutLogs.jsp")
                    .forward(request, response);
            // If the above path doesn't work, try with a different path:
            // request.getRequestDispatcher("/views/client/workoutLogs.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid workout ID format", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Get parameters
        String workoutId = request.getParameter("workoutId");
        String currentExerciseIndex = request.getParameter("exerciseIndex");
        String action = request.getParameter("action");

        // TODO: Save the current exercise data if needed
        // String weight1 = request.getParameter("weight1");
        // String reps1 = request.getParameter("reps1");
        // ... save to database if required

        // Determine the next URL based on action
        if ("next".equals(action)) {
            // Navigate to next exercise
            int nextIndex = Integer.parseInt(currentExerciseIndex) + 1;
            response.sendRedirect(request.getContextPath() +
                    "/StartExercises?workoutId=" + workoutId +
                    "&exerciseIndex=" + nextIndex);
        } else if ("prev".equals(action)) {
            // Navigate to previous exercise
            int prevIndex = Integer.parseInt(currentExerciseIndex) - 1;
            if (prevIndex < 0) prevIndex = 0;
            response.sendRedirect(request.getContextPath() +
                    "/client/StartExercises?workoutId=" + workoutId +
                    "&exerciseIndex=" + prevIndex);
        } else if ("finish".equals(action)) {
            // Finish the workout
            response.sendRedirect(request.getContextPath() + "/workoutOptionss?page=workoutStats");
        } else {
            // Default back to the first exercise
            response.sendRedirect(request.getContextPath() +
                    "/client/StartExercises?workoutId=" + workoutId);
        }
    }
}