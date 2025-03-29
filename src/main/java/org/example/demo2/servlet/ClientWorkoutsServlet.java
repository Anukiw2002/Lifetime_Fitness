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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet("/instructor/clientWorkouts")
public class ClientWorkoutsServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ClientWorkoutDAO clientWorkoutDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        // Get and validate userId
        Long userId;
        try {
            userId = Long.parseLong(request.getParameter("userId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            return;
        }

        try {
            // Get client information
            Client client = clientDAO.findById(userId);
            if (client == null) {
                response.sendRedirect("searchClient");
                return;
            }

            // Get workouts
            List<ClientWorkout> workouts = clientWorkoutDAO.findByUserId(userId);

            // Set attributes
            request.setAttribute("client", client);
            request.setAttribute("workouts", workouts);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/instructor/client-workouts.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}
