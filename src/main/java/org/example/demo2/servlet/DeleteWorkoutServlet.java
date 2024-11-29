package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ClientWorkoutDAO;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructor/deleteWorkout")
public class DeleteWorkoutServlet extends HttpServlet {
    private ClientWorkoutDAO clientWorkoutDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO and DB connection
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session validity and user role
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // Redirect to landing page if session is invalid or user is not logged in
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Get the parameters from the request
        String workoutIdStr = request.getParameter("workoutId");
        String clientPhone = request.getParameter("clientPhone");

        // Log the received parameters for debugging
        System.out.println("Received workoutId: " + workoutIdStr + " clientPhone: " + clientPhone);

        try {
            // Parse workoutId and handle potential parsing errors
            Long workoutId = Long.parseLong(workoutIdStr);

            // Attempt to delete the workout
            clientWorkoutDAO.delete(workoutId);

            // Log successful deletion and redirect
            System.out.println("Workout with ID " + workoutId + " deleted successfully.");
            response.sendRedirect("clientWorkouts?phoneNumber=" + clientPhone);

        } catch (NumberFormatException e) {
            // Handle invalid workoutId format
            System.out.println("Invalid workoutId format: " + workoutIdStr);
            response.sendRedirect("searchClient");
        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
            throw new ServletException("Database error occurred during workout deletion", e);
        }
    }
}
