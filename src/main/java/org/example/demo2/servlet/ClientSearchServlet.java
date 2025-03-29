package org.example.demo2.servlet;

import jakarta.servlet.http.HttpSession;
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

@WebServlet("/instructor/searchClient")
public class ClientSearchServlet extends HttpServlet {
    private ClientDAO clientDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check authorization first
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        // Check if we're searching by ID
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null && !userIdParam.trim().isEmpty()) {
            try {
                Long userId = Long.parseLong(userIdParam);
                Client client = clientDAO.findById(userId);

                if (client != null) {
                    // Store client info in session
                    HttpSession session = request.getSession();
                    session.setAttribute("clientId", client.getId());
                    session.setAttribute("userId", client.getUserId());
                    session.setAttribute("clientName", client.getName());

                    // Redirect to client workouts page using ID
                    response.sendRedirect("clientWorkouts?userId=" + userId);
                } else {
                    request.setAttribute("errorMessage", "No client found with this ID");
                    request.getRequestDispatcher("/WEB-INF/views/instructor/client-search.jsp")
                            .forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid client ID format");
                request.getRequestDispatcher("/WEB-INF/views/instructor/client-search.jsp")
                        .forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        } else {
            // First time visiting the page or no ID entered
            request.getRequestDispatcher("/WEB-INF/views/instructor/client-search.jsp")
                    .forward(request, response);
        }
    }
}