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
        String page = request.getParameter("page");

        try {

            if (pathInfo == null || pathInfo.equals("/")) {
                request.getRequestDispatcher("/WEB-INF/views/instructor/searchClient.jsp")
                        .forward(request, response);
                return;
            }


            if (pathInfo.equals("/list")) {
                String clientPhone = (String) request.getSession().getAttribute("clientPhone");
                if (clientPhone != null) {
                    List<ClientWorkout> workouts = workoutDAO.findByClientPhone(clientPhone);
                    Client client = clientDAO.findByPhoneNumber(clientPhone);

                    request.setAttribute("client", client);
                    request.setAttribute("workouts", workouts);
                    request.getRequestDispatcher("/WEB-INF/views/instructor/listWorkout.jsp")
                            .forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/workoutOptions");
                }
                return;
            }


            if (pathInfo.startsWith("/details/")) {
                String workoutId = pathInfo.substring(9);
                ClientWorkout workout = workoutDAO.findWithExercises(Long.parseLong(workoutId));
                if (workout != null) {
                    List<WorkoutExercise> exercises = workout.getExercises();
                    request.setAttribute("workout", workout);
                    request.setAttribute("exercises", exercises);
                    request.getRequestDispatcher("/WEB-INF/views/instructor/workoutDetails.jsp")
                            .forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/workoutOptions");
                }
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

            if (pathInfo != null && pathInfo.equals("/search")) {
                String clientPhone = request.getParameter("clientPhone");

                if (clientPhone != null && !clientPhone.trim().isEmpty()) {
                    Client client = clientDAO.findByPhoneNumber(clientPhone);

                    if (client != null) {

                        HttpSession session = request.getSession();
                        session.setAttribute("clientPhone", clientPhone);
                        session.setAttribute("clientName", client.getName());


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