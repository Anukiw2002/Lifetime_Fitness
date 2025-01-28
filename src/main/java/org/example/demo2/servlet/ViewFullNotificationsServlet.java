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

@WebServlet("/viewNotification")
public class ViewFullNotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the session object
        HttpSession session = req.getSession(false);

        // Retrieve the user role from the session
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        // Log the retrieved user role
        System.out.println("User role from session: " + userRole);

        // Validate the user role
        if (userRole == null || (!userRole.equals("client") && !userRole.equals("instructor"))) {
            System.out.println("Invalid or missing user role.");
            req.setAttribute("errorMessage", "Invalid or missing user role.");
            req.getRequestDispatcher("/WEB-INF/views/client/clientNotification.jsp").forward(req, resp);
            return;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT title, description, created_at FROM notifications WHERE recipient_role = ? OR recipient_role = 'both' ORDER BY created_at DESC")) {

            // Set the role parameter in the SQL query
            preparedStatement.setString(1, userRole);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = new ArrayList<>();

            // Fetch notifications from the database
            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setTitle(resultSet.getString("title"));
                notification.setDescription(resultSet.getString("description"));
                notification.setTimeAge(resultSet.getTimestamp("created_at").toString());

                // Log each notification fetched
                System.out.println("Fetched notification: " + notification);

                notifications.add(notification);
            }

            // Log the total number of notifications fetched
            System.out.println("Total notifications fetched: " + notifications.size());

            // Set the notifications attribute for the JSP
            req.setAttribute("notifications", notifications);

        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error while fetching notifications: " + e.getMessage());
            e.printStackTrace();

            // Set error message for the JSP
            req.setAttribute("errorMessage", "An error occurred while retrieving notifications.");
        }

        // Forward the request to the clientNotification.jsp
        req.getRequestDispatcher("/WEB-INF/views/client/clientNotification.jsp").forward(req, resp);
    }
}
