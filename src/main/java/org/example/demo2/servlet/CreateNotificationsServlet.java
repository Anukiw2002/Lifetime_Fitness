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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         String notificationMassage = request.getParameter("notificationMassage");
         String targetGroup = request.getParameter("targetGroup");

         if (notificationMassage == null || notificationMassage.trim().isEmpty()){
             request.setAttribute("errorMassage", "Notification massage cannot be empty");
             request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
             return;
         }

         if ("customer".equals(targetGroup)){
             sendToCustomers(notificationMassage);
         } else if ("instructor".equals(targetGroup)){
             sendToInstructors(notificationMassage);
         } else if ("both".equals(targetGroup)){
             sendToCustomers(notificationMassage);
             sendToInstructors(notificationMassage);
         }

         request.setAttribute("successMassage", "Notification sent successfully");
         request.getRequestDispatcher("/WEB-INF/views/owner/createNotifications.jsp").forward(request, response);
    }

    private void sendToCustomers(String notificationMassage){
        System.out.println("Sending notification to customers: " + notificationMassage);

        String sql = "INSERT INTO notifications (massage, recipient_role) VALUES (?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, notificationMassage);
            preparedStatement.setString(2, "customer");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Notification saved to the database");
            } else {
                System.out.println("Failed to save notification to the database");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to customers: " + e.getMessage());
        }

    }

    private void sendToInstructors(String notificationMassage){
        System.out.println("Sending notification to instructors: " + notificationMassage);

        String sql = "INSERT INTO notifications (massage, recipient_role) VALUES (?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, notificationMassage);
            preparedStatement.setString(2, "instructor");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification saved to the database");
            } else {
                System.out.println("Failed to save notification to the database");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to send the notifications to instructors: " + e.getMessage());
        }
    }
}
