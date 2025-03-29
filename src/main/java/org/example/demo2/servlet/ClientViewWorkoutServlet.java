package org.example.demo2.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clientWorkouts")
public class ClientViewWorkoutServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ClientWorkoutDAO workoutDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
        this.workoutDAO = new ClientWorkoutDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get client ID from request parameter
            String clientIdParam = request.getParameter("clientId");
            if (clientIdParam == null || clientIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client ID is required");
                return;
            }

            Long clientId = Long.parseLong(clientIdParam);

            // Fetch client and workouts
            Client client = clientDAO.getById(clientId);
            if (client == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
                return;
            }

            List<ClientWorkout> workouts = workoutDAO.findByUserId(clientId);

            // Set attributes for JSP
            request.setAttribute("client", client);
            request.setAttribute("workouts", workouts);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/clientWorkouts.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Client ID format");
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}