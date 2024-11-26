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
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructor/workoutDetails")
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
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
                // Log error and redirect
                System.out.println("No workout found with ID: " + workoutId);
                response.sendRedirect("searchClient");
                return;
            }

            // Log workout details for debugging
            System.out.println("Found workout: " + workout.getWorkoutName());
            System.out.println("Number of exercises: " +
                    (workout.getExercises() != null ? workout.getExercises().size() : 0));

            // Set the workout in request scope
            request.setAttribute("workout", workout);

            // Forward to the JSP page
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