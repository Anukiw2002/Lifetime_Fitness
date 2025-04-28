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
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;
        System.out.println("User role from session: " + userRole);
        int user_id = (int)req.getSession().getAttribute("userId");
        System.out.println("user_id in viewNotification: " + user_id);

        if (userRole == null || (!userRole.equals("client") && !userRole.equals("instructor"))) {
            System.out.println("Invalid or missing user role.");
            req.setAttribute("errorMessage", "Invalid or missing user role.");
            req.getRequestDispatcher("/WEB-INF/views/client/clientNotification.jsp").forward(req, resp);
            return;
        }
        if (userRole.equals("client")) {
            userRole = "customer";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id, title, description, created_at \n" +
                             "FROM notifications \n" +
                             "WHERE user_id = ? \n" +
                             "  AND is_read = false \n" +
                             "  AND (recipient_role = ? OR recipient_role = 'both') \n" +
                             "ORDER BY created_at DESC")) {

            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "SELECT COUNT(*) AS unread_count FROM notifications WHERE user_id = ? AND is_read = false"
            );
            preparedStatement2.setInt(1, user_id);
            ResultSet countResult = preparedStatement2.executeQuery();

            if (countResult.next()) { //code for the unread count of notifications
                int unread_count = countResult.getInt("unread_count");
                req.setAttribute("unread_count", unread_count);
                System.out.println("Unread count" + unread_count);
            }

            if (connection != null) {
                System.out.println("Database connection successful.");
            } else {
                System.out.println("Failed to establish database connection.");
                req.setAttribute("errorMessage", "Database connection failed.");
                req.getRequestDispatcher("/WEB-INF/views/client/clientNotification.jsp").forward(req, resp);
                return;
            }

            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, userRole);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            System.out.println("Fetching notifications for role: " + userRole);

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setId(resultSet.getInt("id"));
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

        String forwardPage;
        if (userRole.equals("customer")) {
            forwardPage = "/WEB-INF/views/client/clientNotification.jsp";
        } else {
            forwardPage = "/WEB-INF/views/instructor/instructorNotifications.jsp";
        }
        req.getRequestDispatcher(forwardPage).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        int user_id = (int)session.getAttribute("userId");
        String notificationIdParam = req.getParameter("id");

        int notificationId = Integer.parseInt(notificationIdParam);

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE notifications SET is_read = TRUE WHERE user_id = ? AND id = ?")){

            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2,notificationId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated notification ID: " + notificationId);
            }

            resp.sendRedirect(req.getContextPath() + "/viewNotification");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");

        }
    }
}
