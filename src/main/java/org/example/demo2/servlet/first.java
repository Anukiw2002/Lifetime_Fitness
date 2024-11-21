package org.example.demo2.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection; // Your database connection utility

@WebServlet("/first")
public class first extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = DBConnection.getConnection()) {
            // Query to fetch approved emails from the database
            String query = "SELECT email FROM approved_emails";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Store the emails in a list
            List<String> approvedEmails = new ArrayList<>();
            while (rs.next()) {
                approvedEmails.add(rs.getString("email"));
            }

            // Debugging: Log fetched emails to the console
            System.out.println("Fetched emails: " + approvedEmails);

            // Set the list of emails as a request attribute
            request.setAttribute("approvedEmails", approvedEmails);

            // Forward the request to the JSP page
            request.getRequestDispatcher("/WEB-INF/views/owner/first.jsp").forward(request, response);
        } catch (Exception e) {
            // Log the exception and send an error response
            System.err.println("Error fetching emails: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching approved emails.");
        }
    }
}
