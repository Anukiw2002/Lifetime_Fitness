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
import java.sql.ResultSet;
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

        String getCustomerSql = "SELECT id FROM users WHERE role = 'client'";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCustomerSql);
             ResultSet resultSet = preparedStatement.executeQuery()
             )

        {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String insertNotificationSql = "INSERT INTO notifications(title, description, recipient_role, user_id,is_read,created_at) VALUES (?,?,?,?,?, NOW())";

                try(PreparedStatement preparedStatement1 = connection.prepareStatement(insertNotificationSql)){
                    preparedStatement1.setString(1, notificationTitle);
                    preparedStatement1.setString(2, notificationMessage);
                    preparedStatement1.setString(3,"customer");
                    preparedStatement1.setInt(4, id);
                    preparedStatement1.setBoolean(5, false);

                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Notification saved to the database for customer with user_id " + id);
                    } else {
                        System.out.println("Failed to save notification to the database for customer with user_id " + id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to customers: " + e.getMessage());
        }
    }

    private void sendToInstructors(String notificationTitle, String notificationMessage) {
        System.out.println("Sending notification to instructors: " + notificationMessage);

        String getInstructorSql = "SELECT id FROM users WHERE role = 'instructor'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getInstructorSql);
             ResultSet resultset = preparedStatement.executeQuery()) {

            while(resultset.next()){
                int id = resultset.getInt("id");
                String insertSql = "INSERT INTO notifications(title,description, recipient_role, user_id,is_read,created_at) values (?,?,?,?,?,NOW())";
                try(PreparedStatement preparedStatement1 = connection.prepareStatement(insertSql)){
                    preparedStatement1.setString(1, notificationTitle);
                    preparedStatement1.setString(2, notificationMessage);
                    preparedStatement1.setString(3,"instructor");
                    preparedStatement1.setInt(4, id);
                    preparedStatement1.setBoolean(5, false);

                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Notification saved to the database for instructor with user_id " + id);
                    }else {
                        System.out.println("Failed to save notification to the database for instructor with user_id " + id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to instructors: " + e.getMessage());
        }
    }
}
