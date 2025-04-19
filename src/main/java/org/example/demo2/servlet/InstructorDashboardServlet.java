package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ClientDashboardDAO;
import org.example.demo2.dao.InstructorDashboardDAO;
import org.example.demo2.dao.NotificationsDAO;
import org.example.demo2.model.WorkoutCounts;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructorDashboard")
public class InstructorDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int user_id = (int)session.getAttribute("userId");

        boolean hasUnread = NotificationsDAO.hasUnreadNotifications(user_id);
        request.setAttribute("hasUnread", hasUnread);
        InstructorDashboardDAO dao = new InstructorDashboardDAO();
        int count = 0;
        int countWorkout = 0;
        int reportCount = 0;
        WorkoutCounts counts = null;
        try {
            count = dao.getActiveMembers();
            reportCount = dao.getReports();
            countWorkout = dao.getWorkouts();
            counts = dao.getDayWorkouts();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("ActiveMembers", count);
        request.setAttribute("countWorkout", countWorkout);
        request.setAttribute("reportCount", reportCount);
        request.setAttribute("todayCount", counts.getToday());
        request.setAttribute("yesterdayCount", counts.getYesterday());
        request.setAttribute("tomorrowCount", counts.getTomorrow());



        request.getRequestDispatcher("/WEB-INF/views/instructor/instructor-dashboard.jsp").forward(request, response);

    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        ClientDashboardDAO dao = new ClientDashboardDAO();


        // Forward the request to navbar.html
        request.getRequestDispatcher("/WEB-INF/views/instructor/upcomingSessions.jsp").forward(request, response);
    }

}
