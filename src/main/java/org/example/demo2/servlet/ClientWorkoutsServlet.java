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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String phoneNumber = request.getParameter("phoneNumber");
        String clientPhone = request.getParameter("clientPhone");


        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            phoneNumber = clientPhone;
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            response.sendRedirect("searchClient");
            return;
        }

        try {

            Client client = clientDAO.findByPhoneNumber(phoneNumber);
            if (client == null) {
                response.sendRedirect("searchClient");
                return;
            }

            if (session.getAttribute("clientUserId") == null) {
                session.setAttribute("clientUserId", client.getUserId());
            }


            List<ClientWorkout> workouts = clientWorkoutDAO.findByClientPhone(phoneNumber);


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


            request.getRequestDispatcher("/WEB-INF/views/instructor/instructor-workouts.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}