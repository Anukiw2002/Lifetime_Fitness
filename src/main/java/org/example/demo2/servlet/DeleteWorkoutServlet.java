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
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        try {
            Long workoutId = Long.parseLong(request.getParameter("workoutId"));
            clientWorkoutDAO.delete(workoutId);
            response.sendRedirect("clientWorkouts?phoneNumber=" + request.getParameter("clientPhone"));

        } catch (NumberFormatException e) {
            response.sendRedirect("searchClient");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
