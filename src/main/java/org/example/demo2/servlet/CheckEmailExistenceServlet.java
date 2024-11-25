package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CheckEmailExistence")
public class CheckEmailExistenceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Email is required.\"}");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check in `users` table
            String userQuery = "SELECT 1 FROM users WHERE email = ?";
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
                userStmt.setString(1, email);
                ResultSet userRs = userStmt.executeQuery();

                if (!userRs.next()) {
                    // Email not in `users`
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"not_in_users\", \"message\": \"This email is not registered in the system.\"}");
                    return;
                }
            }

            // Check in `approved_emails` table
            String approvedQuery = "SELECT 1 FROM approved_emails WHERE email = ?";
            try (PreparedStatement approvedStmt = conn.prepareStatement(approvedQuery)) {
                approvedStmt.setString(1, email);
                ResultSet approvedRs = approvedStmt.executeQuery();

                if (approvedRs.next()) {
                    // Email in `approved_emails`
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"already_approved\", \"message\": \"Report is already added.\"}");
                    return;
                }
            }

            // Email can be added
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"not_approved\", \"message\": \"Email is verified and can be added.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Database error occurred.\"}");
        }
    }
}

