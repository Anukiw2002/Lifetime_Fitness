package org.example.demo2.servlet;

import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/workoutOptions", "/workoutOptions/*"})
public class ViewWorkoutServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ClientWorkoutDAO workoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        clientDAO = new ClientDAO(dbConnection);
        workoutDAO = new ClientWorkoutDAO(dbConnection);
        workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            // Default to search page
            if (pathInfo == null || pathInfo.equals("/")) {
                request.getRequestDispatcher("/WEB-INF/views/instructor/searchClient.jsp")
                        .forward(request, response);
                return;
            }

            // Handle workout list page
            if (pathInfo.equals("/list")) {
                String clientPhone = (String) request.getSession().getAttribute("clientPhone");

                if (clientPhone != null) {
                    Client client = clientDAO.findByPhoneNumber(clientPhone);
                    if (client == null) {
                        response.sendRedirect(request.getContextPath() + "/workoutOptions");
                        return;
                    }

                    List<ClientWorkout> workouts = workoutDAO.findByUserId(client.getUserId());

                    request.setAttribute("client", client);
                    request.setAttribute("workouts", workouts);
                    request.getRequestDispatcher("/WEB-INF/views/instructor/listWorkout.jsp")
                            .forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/workoutOptions");
                }
                return;
            }

            // Handle workout details
            if (pathInfo.startsWith("/details/")) {
                String workoutIdStr = pathInfo.substring(9); // Extract ID from URL
                if (!workoutIdStr.matches("\\d+")) {
                    response.sendRedirect(request.getContextPath() + "/workoutOptions");
                    return;
                }

                Long workoutId = Long.parseLong(workoutIdStr);
                List<WorkoutExercise> exercises = workoutExerciseDAO.findByWorkoutId(workoutId);
                request.setAttribute("exercises", exercises);
                request.getRequestDispatcher("/WEB-INF/views/instructor/workoutDetails.jsp")
                        .forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            // Handle search
            if (pathInfo != null && pathInfo.equals("/search")) {
                String clientPhone = request.getParameter("clientPhone");

                if (clientPhone != null && !clientPhone.trim().isEmpty()) {
                    Client client = clientDAO.findByPhoneNumber(clientPhone);

                    if (client != null) {
                        // Store in session
                        HttpSession session = request.getSession();
                        session.setAttribute("clientPhone", client.getClientPhone()); // Updated method name
                        session.setAttribute("userId", client.getUserId());
                        session.setAttribute("address", client.getAddress());
                        session.setAttribute("dateOfBirth", client.getDateOfBirth());
                        session.setAttribute("emergencyContactName", client.getEmergencyContactName());
                        session.setAttribute("emergencyContactNumber", client.getEmergencyContactNumber());

                        // Redirect to workout list
                        response.sendRedirect(request.getContextPath() + "/workoutOptions/list");
                    } else {
                        request.setAttribute("error", "No client found with this phone number");
                        request.getRequestDispatcher("/WEB-INF/views/instructor/selectUser.jsp")
                                .forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Please enter a phone number");
                    request.getRequestDispatcher("/WEB-INF/views/instructor/selectUser.jsp")
                            .forward(request, response);
                }
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
