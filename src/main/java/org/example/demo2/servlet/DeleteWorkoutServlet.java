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
        try {
            DBConnection dbConnection = new DBConnection();
            this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DeleteWorkoutServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DeleteWorkoutServlet: doPost method called");


        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            System.out.println("Session invalid or user not logged in");
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"instructor".equals(userRole)) {
            System.out.println("User is not an instructor. Role: " + userRole);
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }


        String workoutIdStr = request.getParameter("workoutId");
        String clientPhone = request.getParameter("clientPhone"); // Keep for redirect


        System.out.println("Received workoutId: " + workoutIdStr + " clientPhone: " + clientPhone);


        if (workoutIdStr == null || workoutIdStr.trim().isEmpty()) {
            System.out.println("workoutId parameter is missing or empty");
            response.sendRedirect(request.getContextPath() + "/instructor/searchClient");
            return;
        }

        if (clientPhone == null || clientPhone.trim().isEmpty()) {
            System.out.println("clientPhone parameter is missing or empty");
            clientPhone = ""; // Set empty to avoid null pointer in redirect
        }

        try {

            Long workoutId = Long.parseLong(workoutIdStr);


            clientWorkoutDAO.delete(workoutId);


            System.out.println("Attempted to delete workout with ID " + workoutId);


            response.sendRedirect(request.getContextPath() + "/instructor/clientWorkouts?phoneNumber=" + clientPhone);

        } catch (NumberFormatException e) {

            System.out.println("Invalid workoutId format: " + workoutIdStr);
            response.sendRedirect(request.getContextPath() + "/instructor/searchClient");
        } catch (SQLException e) {

            System.out.println("SQL Exception during workout deletion: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Database error occurred during workout deletion", e);
        } catch (Exception e) {

            System.out.println("Unexpected error during workout deletion: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error occurred during workout deletion", e);
        }
    }
}