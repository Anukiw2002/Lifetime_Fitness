package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.ClientDAO;
import org.example.demo2.model.Client;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/viewMember")
public class ViewMemberProfileServlet extends HttpServlet {
    private ClientDAO clientDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Only this check is needed!
        if (!SessionUtils.isUserAuthorized(request, response, "owner", "instructor")) {
            return;
        }

        int userId = Integer.parseInt(request.getParameter("id"));

        Client client = null;
        try {
            client = clientDAO.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("client", client);

        request.getRequestDispatcher("/WEB-INF/views/owner/viewMember.jsp").forward(request,response);

    }
}
