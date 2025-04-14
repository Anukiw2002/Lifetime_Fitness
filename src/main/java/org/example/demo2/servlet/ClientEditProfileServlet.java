package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ClientDAO;
import org.example.demo2.dao.ReportDAO;
import org.example.demo2.model.Client;
import org.example.demo2.model.Report;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clientEditProfile")
public class ClientEditProfileServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ReportDAO reportDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
        reportDAO = new ReportDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");


        try {
            Client client = clientDAO.findByUserId(userId);


            // Set attributes for the JSP
            request.setAttribute("client", client);

            Report report = reportDAO.getById(userId);
            request.setAttribute("report", report);

        request.getRequestDispatcher("/WEB-INF/views/client/editProfile.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}
