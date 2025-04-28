package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.BookSession;
import org.example.demo2.model.Instructor;
import org.example.demo2.model.WorkoutCounts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/instructorDashboard")
public class InstructorDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int user_id = (int)session.getAttribute("userId");
        request.setAttribute("userId", user_id);

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        Instructor instructor = instructorOnBoardingDAO.getInstructorById(user_id);

        request.setAttribute("instructor", instructor);

        BookSessionDAO bookSessionDAO = new BookSessionDAO();
        List<BookSession> bookings =  bookSessionDAO.getTodayBookedSessions();

        request.setAttribute("bookings", bookings);

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

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        ClientDashboardDAO dao = new ClientDashboardDAO();



        request.getRequestDispatcher("/WEB-INF/views/instructor/upcomingSessions.jsp").forward(request, response);
    }

}
