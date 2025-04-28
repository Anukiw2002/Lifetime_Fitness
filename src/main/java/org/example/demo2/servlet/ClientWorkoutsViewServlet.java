package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.demo2.dao.UserDAO;
import org.example.demo2.dao.ClientWorkoutDAO;
import org.example.demo2.model.Client;
import org.example.demo2.model.User;
import org.example.demo2.model.ClientWorkout;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;
import org.example.demo2.dao.ClientDAO;


import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;

@WebServlet("/clientWorkoutView")
public class ClientWorkoutsViewServlet extends HttpServlet {
    private UserDAO userDAO;
    private ClientWorkoutDAO clientWorkoutDAO;

    @Override
    public void init() throws ServletException {
        try {
            DBConnection dbConnection = new DBConnection();
            userDAO = new UserDAO();
            clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAOs: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            return;
        }
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        try {
            DBConnection dbConnection = new DBConnection();
            ClientDAO clientDAO = new ClientDAO(dbConnection);

            Client client = clientDAO.findByUserId(userId);
            if (client == null) {
                response.sendRedirect("clientWorkoutView");
                return;
            }


            if (session.getAttribute("clientUserId") == null) {
                session.setAttribute("clientUserId", client.getUserId());
            }

            List<ClientWorkout> workouts = clientWorkoutDAO.findByUserId(userId);

            for (ClientWorkout workout : workouts) {
                if (workout.getCreatedAt() != null) {
                    Date date = Date.from(workout.getCreatedAt()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                    workout.setCreatedAtDate(date);
                }
            }

            request.setAttribute("client", client);
            request.setAttribute("workouts", workouts);

            request.getRequestDispatcher("/WEB-INF/views/client/clientWorkout.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

}