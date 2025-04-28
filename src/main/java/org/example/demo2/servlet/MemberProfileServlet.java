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
import org.example.demo2.model.ClientMembership;
import org.example.demo2.dao.ClientMembershipDAO;
import org.example.demo2.model.Report;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/memberProfile")
public class MemberProfileServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ClientMembershipDAO membershipDAO;
    private ReportDAO reportDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
        membershipDAO = new ClientMembershipDAO(dbConnection);
        reportDAO = new ReportDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");


        try {
            Client client = clientDAO.findByUserId(userId);
            request.setAttribute("client", client);


            List<ClientMembership> membership = membershipDAO.getClientMembership(userId);
            request.setAttribute("membership", membership);


            Report report = reportDAO.getById(userId);
            request.setAttribute("report", report);


            request.getRequestDispatcher("/WEB-INF/views/client/memberProfile.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}
