package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.DashboardDAO;
import org.example.demo2.dao.NotificationsDAO;
import org.example.demo2.dao.ReportDAO;
import org.example.demo2.model.UserWeightData;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/clientDashboard")
public class ClientDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String email = (String) session.getAttribute("email");
        System.out.println("email" +email);
        int user_id = (int)session.getAttribute("userId");

        // Use NotificationsDAO to check for unread notifications
        boolean hasUnread = NotificationsDAO.hasUnreadNotifications(user_id);  // Call DAO method to check unread notifications

        req.setAttribute("hasUnread", hasUnread);  // Pass the boolean value to the JSP

        // Fetch user information (name) - This is still directly done in the servlet for simplicity
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT full_name || ' ' || username AS name FROM users WHERE id = ?"
            );
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                req.setAttribute("userName", resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ReportDAO reportDAO = new ReportDAO();
        UserWeightData weightData = reportDAO.getWeightByEmail(email);

        DashboardDAO dashboardDAO = new DashboardDAO();
        int workoutCount = dashboardDAO.getWorkoutCountById(user_id);
        int streak = dashboardDAO.getWorkoutSteakById(user_id);

        req.setAttribute("beginningWeight", weightData.getBeginningWeight());
        req.setAttribute("currentWeight", weightData.getCurrentWeight());
        req.setAttribute("targetWeight", weightData.getTargetWeight());
        req.setAttribute("workoutCount", workoutCount);
        req.setAttribute("streak", streak);
        System.out.println("Streak: " + streak);
        System.out.println("workouts:" + workoutCount);
        // Forward the request to the JSP
        req.getRequestDispatcher("/WEB-INF/views/client/client-dashboard.jsp").forward(req, resp);
    }
}
