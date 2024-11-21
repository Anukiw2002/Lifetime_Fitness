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

        System.out.println("Received email: " + email); // Debug log

        if (email == null || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required.");
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            // Check if email exists in the approved_emails table
            String query = "SELECT 1 FROM approved_emails WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Email found in approved_emails table
                    System.out.println("Email exists in approved_emails: " + email); // Debug log
                    out.write("{\"status\": \"exists\"}");
                } else {
                    // Email not found in approved_emails table
                    System.out.println("Email does not exist in approved_emails: " + email); // Debug log
                    out.write("{\"status\": \"not_exists\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"status\": \"error\"}");
        }
    }
}
