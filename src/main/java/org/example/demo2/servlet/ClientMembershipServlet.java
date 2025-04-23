package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.ClientMembershipDAO;
import org.example.demo2.model.ClientMembership;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clientMemberships")
public class ClientMembershipServlet extends HttpServlet {
    private ClientMembershipDAO membershipDAO;

    @Override
    public void init() throws ServletException {
        // Create your DBConnection instance as needed by your project.
        DBConnection dbConnection = new DBConnection();
        membershipDAO = new ClientMembershipDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Add the authorization check at the beginning
        if (!SessionUtils.isUserAuthorized(request, response, "owner", "instructor")) {
            return;
        }

        try {
            List<ClientMembership> memberships = membershipDAO.getAllMemberships();
            request.setAttribute("memberships", memberships);
            request.getRequestDispatcher("/WEB-INF/views/owner/memberManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving memberships", e);
        }
    }
}
