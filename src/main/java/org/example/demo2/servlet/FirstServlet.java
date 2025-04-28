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
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection; // Your database connection utility

@WebServlet("/first")
public class FirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                System.out.println("Connection is null");
                return;
            }


            String query = "SELECT email FROM approved_emails";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();


            List<String> approvedEmails = new ArrayList<>();
            while (rs.next()) {
                approvedEmails.add(rs.getString("email"));
            }


            System.out.println("Fetched emails: " + approvedEmails);


            request.setAttribute("approvedEmails", approvedEmails);


            request.getRequestDispatcher("/WEB-INF/views/owner/first.jsp").forward(request, response);
        } catch (Exception e) {

            System.err.println("Error fetching emails: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching approved emails.");
        }
    }
}