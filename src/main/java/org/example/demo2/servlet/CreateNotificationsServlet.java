package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/createNotificationRedirection")
public class CreateNotificationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String notificationTitle = request.getParameter("notificationTitle");
        String notificationMessage = request.getParameter("notificationMessage");
        String targetGroup = request.getParameter("targetGroup");

        // Validate inputs
        if (notificationTitle == null || notificationTitle.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Notification title cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
            return;
        }

        if (notificationMessage == null || notificationMessage.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Notification message cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
            return;
        }

        // Send notifications based on the target group
        if ("customers".equals(targetGroup)) {
            sendToCustomers(notificationTitle, notificationMessage);
        } else if ("instructors".equals(targetGroup)) {
            sendToInstructors(notificationTitle, notificationMessage);
        } else if ("both".equals(targetGroup)) {
            sendToCustomers(notificationTitle, notificationMessage);
            sendToInstructors(notificationTitle, notificationMessage);
        }

        // Redirect back with success message
        request.setAttribute("successMessage", "Notification sent successfully.");
        request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
    }

    private void sendToCustomers(String notificationTitle, String notificationMessage) {
        System.out.println("Sending notification to customers: " + notificationMessage);

        String sql = "INSERT INTO notifications (title, description, recipient_role) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, notificationTitle);
            preparedStatement.setString(2, notificationMessage);
            preparedStatement.setString(3, "customer");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification saved to the database for customers.");
            } else {
                System.out.println("Failed to save notification to the database for customers.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to customers: " + e.getMessage());
        }
    }

    private void sendToInstructors(String notificationTitle, String notificationMessage) {
        System.out.println("Sending notification to instructors: " + notificationMessage);

        String sql = "INSERT INTO notifications (title, description, recipient_role) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, notificationTitle);
            preparedStatement.setString(2, notificationMessage);
            preparedStatement.setString(3, "instructor");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification saved to the database for instructors.");
            } else {
                System.out.println("Failed to save notification to the database for instructors.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to instructors: " + e.getMessage());
        }
    }
}
