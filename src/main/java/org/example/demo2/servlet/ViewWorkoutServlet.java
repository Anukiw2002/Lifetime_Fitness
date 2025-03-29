package org.example.demo2.servlet;

import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

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
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                request.getRequestDispatcher("/WEB-INF/views/instructor/searchClient.jsp")
                        .forward(request, response);
                return;
            }

            if (pathInfo.equals("/list")) {
                handleWorkoutList(request, response);
                return;
            }

            if (pathInfo.startsWith("/details/")) {
                handleWorkoutDetails(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/workoutOptions");

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        try {
            if (request.getPathInfo() != null && request.getPathInfo().equals("/search")) {
                handleClientSearch(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void handleWorkoutList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/workoutOptions");
            return;
        }

        Client client = clientDAO.findById(userId);
        if (client == null) {
            response.sendRedirect(request.getContextPath() + "/workoutOptions");
            return;
        }

        List<ClientWorkout> workouts = workoutDAO.findByUserId(userId);
        request.setAttribute("client", client);
        request.setAttribute("workouts", workouts);
        request.getRequestDispatcher("/WEB-INF/views/instructor/listWorkout.jsp")
                .forward(request, response);
    }

    private void handleWorkoutDetails(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String workoutIdStr = request.getPathInfo().substring(9);
            Long workoutId = Long.parseLong(workoutIdStr);

            List<WorkoutExercise> exercises = workoutExerciseDAO.findByWorkoutId(workoutId);
            request.setAttribute("exercises", exercises);

            try {
                request.getRequestDispatcher("/WEB-INF/views/instructor/workoutDetails.jsp")
                        .forward(request, response);
            } catch (ServletException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error displaying workout details");
            }

        } catch (NumberFormatException e) {
            forwardWithError(request, response, "Invalid workout ID format");
        } catch (SQLException e) {
            forwardWithError(request, response, "Database error loading workout details");
        }
    }

    private void handleClientSearch(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        try {
            String userIdParam = request.getParameter("userId");

            if (userIdParam == null || userIdParam.trim().isEmpty()) {
                forwardWithError(request, response, "Please enter a user ID");
                return;
            }

            Long userId = Long.parseLong(userIdParam);
            Client client = clientDAO.findById(userId);

            if (client == null) {
                forwardWithError(request, response, "No client found with this ID");
                return;
            }

            // Store client info in session
            HttpSession session = request.getSession();
            session.setAttribute("userId", client.getUserId());
            session.setAttribute("clientName", client.getName());

            // Redirect to workout list
            response.sendRedirect(request.getContextPath() + "/workoutOptions/list");

        } catch (NumberFormatException e) {
            forwardWithError(request, response, "Invalid user ID format");
        }
    }

    // New helper method to handle forwarding with error messages
    private void forwardWithError(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String errorMessage) throws IOException {
        try {
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("/WEB-INF/views/instructor/searchClient.jsp")
                    .forward(request, response);
        } catch (ServletException e) {
            // If forwarding fails, send an error response
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error displaying page: " + errorMessage);
        }
    }
}