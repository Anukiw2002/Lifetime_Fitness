package org.example.demo2.servlet;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/workoutDetails")
public class WorkoutDetailsServlet extends HttpServlet {

    private ClientWorkoutDAO clientWorkoutDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }
        String workoutIdStr = request.getParameter("workoutId");

        if (workoutIdStr == null || workoutIdStr.trim().isEmpty()) {
            response.sendRedirect("searchClient");
            return;
        }

        try {
            Long workoutId = Long.parseLong(workoutIdStr);
            ClientWorkout workout = clientWorkoutDAO.findWithExercises(workoutId);

            if (workout == null) {

                System.out.println("No workout found with ID: " + workoutId);
                response.sendRedirect("searchClient");
                return;
            }


            System.out.println("Found workout: " + workout.getWorkoutName());
            System.out.println("Number of exercises: " +
                    (workout.getExercises() != null ? workout.getExercises().size() : 0));


            request.setAttribute("workout", workout);


            request.getRequestDispatcher("/WEB-INF/views/instructor/workout-details.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("Invalid workout ID: " + workoutIdStr);
            response.sendRedirect("searchClient");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw new ServletException("Database error occurred", e);
        }
    }
}