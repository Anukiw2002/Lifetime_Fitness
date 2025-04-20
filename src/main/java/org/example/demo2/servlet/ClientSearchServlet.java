package org.example.demo2.servlet;

import jakarta.servlet.http.HttpSession;
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


@WebServlet("/instructor/searchClient")
public class ClientSearchServlet extends HttpServlet {
    private ClientDAO clientDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection(); // Assume you have this configured
        this.clientDAO = new ClientDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phoneNumber = request.getParameter("phoneNumber");
        String clientPhone = request.getParameter("clientPhone");

        // Use clientPhone parameter if phoneNumber is not provided
        if (clientPhone != null && !clientPhone.trim().isEmpty()) {
            phoneNumber = clientPhone;
        }

        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            try {
                Client client = clientDAO.findByPhoneNumber(phoneNumber);
                if (client != null) {
                    // Store client ID in session for further operations
                    HttpSession session = request.getSession();
                    session.setAttribute("clientUserId", client.getUserId());

                    // Redirect to client workouts page
                    response.sendRedirect("clientWorkouts?phoneNumber=" + phoneNumber);
                } else {
                    request.setAttribute("errorMessage", "No client found with this phone number");
                    request.getRequestDispatcher("/WEB-INF/views/instructor/instructor-search.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        } else {
            // First time visiting the page or no phone number entered
            request.getRequestDispatcher("/WEB-INF/views/instructor/client-search.jsp").forward(request, response);
        }
    }
}