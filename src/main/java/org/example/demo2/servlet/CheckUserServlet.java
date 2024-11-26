package org.example.demo2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;
@WebServlet("/CheckUser")
public class CheckUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String email = request.getParameter("email");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email is missing in the request."); // Debugging log
            out.write("{\"status\":\"error\", \"message\":\"Email is required.\"}");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            System.out.println("Connected to database."); // Debugging log

            String userQuery = "SELECT email FROM users WHERE email = ?";
            try (PreparedStatement userStmt = con.prepareStatement(userQuery)) {
                userStmt.setString(1, email);
                System.out.println("Executing query: " + userQuery); // Debugging log

                ResultSet userRs = userStmt.executeQuery();
                if (userRs.next()) {
                    System.out.println("Email found in users table: " + email); // Debugging log

                    String checkApprovedQuery = "SELECT email FROM approved_emails WHERE email = ?";
                    try (PreparedStatement approvedStmt = con.prepareStatement(checkApprovedQuery)) {
                        approvedStmt.setString(1, email);
                        ResultSet approvedRs = approvedStmt.executeQuery();

                        if (approvedRs.next()) {
                            System.out.println("Email already in approved_emails: " + email); // Debugging log
                            out.write("{\"status\":\"already_approved\", \"message\":\"Report is already added for this email. Please try editing it.\"}");
                        } else {
                            String insertQuery = "INSERT INTO approved_emails (email) VALUES (?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setString(1, email);
                                insertStmt.executeUpdate();
                            }


                            session.setAttribute("userEmail", email);
                            System.out.println("Email added to approved_emails and session: " + email); // Debugging log
                            out.write("{\"status\":\"not_approved\", \"message\":\"Email is verified successfully!\", \"redirectUrl\":\"/processReport1\"}");
                        }
                    }
                } else {
                    System.out.println("Email not found in users table: " + email); // Debugging log
                    out.write("{\"status\":\"not_in_users\", \"message\":\"This email is not registered in the system.\"}");
                }
            }
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage()); // Debugging log
            e.printStackTrace();
            out.write("{\"status\":\"error\", \"message\":\"An unexpected error occurred.\"}");
        }
    }
}

