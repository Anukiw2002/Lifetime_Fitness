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

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String workoutId = request.getParameter("workoutId");

        String exerciseIndexParam = request.getParameter("exerciseIndex");
        int exerciseIndex = 0;

        if (exerciseIndexParam != null && !exerciseIndexParam.isEmpty()) {
            try {
                exerciseIndex = Integer.parseInt(exerciseIndexParam);
            } catch (NumberFormatException e) {
                exerciseIndex = 0;
            }
        }

        try {
            if (workoutId == null || workoutId.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/memberProfile");
                return;
            }


            ClientWorkout workout = clientWorkoutDAO.findWithExercises(Long.parseLong(workoutId));
            if (workout == null) {
                response.sendRedirect("clientWorkouts");
                return;
            }

            List<WorkoutExercise> exercises = workout.getExercises();


            if (exercises == null || exercises.isEmpty()) {
                // No exercises in this workout
                request.setAttribute("errorMessage", "This workout has no exercises.");
                response.sendRedirect(request.getContextPath() + "/clientWorkouts");
                return;
            }


            if (exerciseIndex >= exercises.size()) {
                exerciseIndex = 0;
            }


            WorkoutExercise currentExercise = exercises.get(exerciseIndex);

            request.setAttribute("workout", workout);
            request.setAttribute("currentExercise", currentExercise);
            request.setAttribute("exerciseIndex", exerciseIndex);
            request.setAttribute("totalExercises", exercises.size());
            request.setAttribute("isFirstExercise", exerciseIndex == 0);
            request.setAttribute("isLastExercise", exerciseIndex == exercises.size() - 1);

            request.getRequestDispatcher("/WEB-INF/views/client/workoutLogs.jsp")
                    .forward(request, response);


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

            response.sendRedirect(request.getContextPath() + "/workoutOptionss?page=workoutStats");
        } else {

            response.sendRedirect(request.getContextPath() +
                    "/client/StartExercises?workoutId=" + workoutId);
        }
    }
}