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
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phoneNumber = request.getParameter("phoneNumber");
        String clientPhone = request.getParameter("clientPhone");


        if (clientPhone != null && !clientPhone.trim().isEmpty()) {
            phoneNumber = clientPhone;
        }

        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            try {
                Client client = clientDAO.findByPhoneNumber(phoneNumber);
                if (client != null) {

                    HttpSession session = request.getSession();
                    session.setAttribute("clientUserId", client.getUserId());


                    response.sendRedirect("clientWorkouts?phoneNumber=" + phoneNumber);
                } else {
                    request.setAttribute("errorMessage", "No client found with this phone number");
                    request.getRequestDispatcher("/WEB-INF/views/instructor/instructor-search.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        } else {

            request.getRequestDispatcher("/WEB-INF/views/instructor/client-search.jsp").forward(request, response);
        }
    }
}