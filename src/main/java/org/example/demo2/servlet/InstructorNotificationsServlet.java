package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Notification;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewInstructorNotification")
public class InstructorNotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        System.out.println("User role from session: " + userRole);

        if (userRole == null || (!userRole.equals("client") && !userRole.equals("instructor"))) {
            System.out.println("Invalid or missing user role.");
            req.setAttribute("errorMessage", "Invalid or missing user role.");
            req.getRequestDispatcher("/WEB-INF/views/instructor/instructorNotification.jsp").forward(req, resp);
            return;
        }
        if (userRole.equals("client")) {
            userRole = "customer"; // Adjust the role to match the database
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT title, description, created_at FROM notifications WHERE recipient_role = ? OR recipient_role = 'both' ORDER BY created_at DESC")) {

            if (connection != null) {
                System.out.println("Database connection successful.");
            } else {
                System.out.println("Failed to establish database connection.");
                req.setAttribute("errorMessage", "Database connection failed.");
                req.getRequestDispatcher("/WEB-INF/views/instructor/instructorNotification.jsp").forward(req, resp);
                return;
            }

            preparedStatement.setString(1, userRole);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            System.out.println("Fetching notifications for role: " + userRole);

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setTitle(resultSet.getString("title"));
                notification.setDescription(resultSet.getString("description"));
                notification.setTimeAge(resultSet.getTimestamp("created_at").toString());
                notifications.add(notification);

                // Print each notification to console
                System.out.println("Fetched Notification: ");
                System.out.println("Title: " + notification.getTitle());
                System.out.println("Description: " + notification.getDescription());
                System.out.println("Created At: " + notification.getTimeAge());
                System.out.println("----------------------------");
            }

            System.out.println("Total notifications fetched: " + notifications.size());
            req.setAttribute("notifications", notifications);
        } catch (SQLException e) {
            System.err.println("Error while fetching notifications: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("errorMessage", "An error occurred while retrieving notifications.");
        }

        req.getRequestDispatcher("/WEB-INF/views/instructor/instructorNotification.jsp").forward(req, resp);
    }
}
