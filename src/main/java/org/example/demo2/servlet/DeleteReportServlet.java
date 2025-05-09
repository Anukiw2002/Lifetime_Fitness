package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/deleteReport")
public class DeleteReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required to delete the report.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {

                String deleteExercisesQuery = "DELETE FROM user_exercises WHERE email = ?";
                try (PreparedStatement exerciseStmt = conn.prepareStatement(deleteExercisesQuery)) {
                    exerciseStmt.setString(1, email);
                    int exerciseRows = exerciseStmt.executeUpdate();
                    System.out.println("Deleted rows in user_exercises: " + exerciseRows);
                }


                String deleteReportQuery = "DELETE FROM user_reports WHERE email = ?";
                try (PreparedStatement reportStmt = conn.prepareStatement(deleteReportQuery)) {
                    reportStmt.setString(1, email);
                    int reportRows = reportStmt.executeUpdate();
                    System.out.println("Deleted rows in user_reports: " + reportRows);
                }


                String deleteEmailQuery = "DELETE FROM approved_emails WHERE email = ?";
                try (PreparedStatement emailStmt = conn.prepareStatement(deleteEmailQuery)) {
                    emailStmt.setString(1, email);
                    int emailRows = emailStmt.executeUpdate();
                    System.out.println("Deleted rows in approved_emails: " + emailRows);
                }

                conn.commit();
                response.sendRedirect(request.getContextPath() + "/first");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting report and email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.");
        }
    }
}
