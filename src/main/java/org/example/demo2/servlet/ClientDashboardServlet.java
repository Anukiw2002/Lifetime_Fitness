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

@WebServlet("/clientDashboard")
public class ClientDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userId")== null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int user_id = (int)session.getAttribute("userId");

        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT full_name || ' ' || username AS name FROM users WHERE id = ?");
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                req.setAttribute("userName", resultSet.getString("name"));
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT COUNT(*) AS unread_count FROM notifications WHERE id = ? AND is_read = FALSE");
            preparedStatement1.setInt(1,user_id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            int unread_count = 0;
            if(resultSet1.next()){
                unread_count = resultSet1.getInt("unread_count");
            }
            req.setAttribute("unread_count",unread_count);

//            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT title, description, created_at FROM notifications WHERE created_at >= NOW() - INTERVAL '5 days' AND id = ? ORDER BY created_at DESC");
//            preparedStatement2.setInt(1,user_id);
//            ResultSet resultSet2 = preparedStatement2.executeQuery();
//            List< Notification > notifications = new ArrayList<>();
//            while (resultSet2.next()){
//                notifications.add(new Notification(
//                        resultSet2.getString("title"),
//                        resultSet2.getString("description"),
//                        resultSet2.getTimestamp("created_at")
//
//                ));
//                notifications.add(notification);
//                // Print to IntelliJ terminal
//                System.out.println("Notification Title: " + notifications.getTitle());
//                System.out.println("Description: " + notifications.getDescription());
//                System.out.println("Created At: " + notifications.getCreatedAt());
//                System.out.println("-----------------------------------------");
//            }
//            req.setAttribute("notifications",notifications);
//        }catch (SQLException e){
//            e.printStackTrace();
//            req.setAttribute("error",e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/views/client/client-dashboard.jsp").forward(req,resp);

    }
}
