package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TotalReports")
public class TotalReportsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        try (Connection con = DBConnection.getConnection()) {
            if (con != null) {
                System.out.println("Database connection established.");
            } else {
                System.out.println("Failed to establish database connection.");
            }
            // Query to fetch approved emails
            String query = "SELECT email FROM approved_emails";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Store the emails in a list
            List<String> approvedEmails = new ArrayList<>();
            while (rs.next()) {
                approvedEmails.add(rs.getString("email"));
            }

            // Set the list as a request attribute
            request.setAttribute("approvedEmails", approvedEmails);
            System.out.println("Fetched emails: " + approvedEmails);

            // Forward to JSP
            request.getRequestDispatcher("/jsp/first.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching approved emails.");
        }

    }
}
