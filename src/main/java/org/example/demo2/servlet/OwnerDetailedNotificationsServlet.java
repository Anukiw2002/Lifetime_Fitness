package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Notification;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/owner/ownerDetailedNotifications")
public class OwnerDetailedNotificationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if user is authorized as "owner"
        if (!SessionUtils.isUserAuthorized(req, resp, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }

        HttpSession session = req.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        System.out.println("User role from the session: " + userRole);

        // Validate user role
        if (userRole == null || !userRole.equals("owner")) {
            System.out.println("Invalid or missing user role");
            req.setAttribute("errorMessage", "Invalid or missing user role");
            req.getRequestDispatcher("/WEB-INF/views/owner/ownerDetailedNotification.jsp").forward(req, resp);
            return;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT title, MAX(description) AS description, MAX(created_at) AS created_at\n" +
                             "FROM notifications\n" +
                             "GROUP BY title\n" +
                             "ORDER BY created_at DESC;")) {

            if (connection != null) {
                System.out.println("Database connection successful.");
            } else {
                System.out.println("Failed to establish database connection.");
                req.setAttribute("errorMessage", "Database connection failed.");
                req.getRequestDispatcher("/WEB-INF/views/owner/ownerDetailedNotification.jsp").forward(req, resp);
                return;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            System.out.println("Fetching notifications for role: " + userRole);

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setTitle(resultSet.getString("title"));
                notification.setDescription(resultSet.getString("description"));
                notification.setTimeAge(resultSet.getTimestamp("created_at").toString());
                notifications.add(notification);
            }

            req.setAttribute("notifications", notifications);
            req.getRequestDispatcher("/WEB-INF/views/owner/ownerDetailedNotification.jsp").forward(req, resp);

        } catch (Exception e) {
            // Catch any exceptions that occur during the database query or request forwarding
            e.printStackTrace();
            req.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/owner/ownerDetailedNotification.jsp").forward(req, resp);
        }
    }
}
