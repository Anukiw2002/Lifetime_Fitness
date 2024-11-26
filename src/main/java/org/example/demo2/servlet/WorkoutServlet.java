package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/workoutOptionss") // Map this servlet to /workoutOptions
public class WorkoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        // Get the "page" parameter to decide which page to forward to
        String page = request.getParameter("page");

        if ("newWorkout".equals(page)) {
            // Forward to the new workout JSP page
            request.getRequestDispatcher("/WEB-INF/views/instructor/newWorkout.jsp").forward(request, response);
        }
        else if ("workout".equals(page)){
            request.getRequestDispatcher("/WEB-INF/views/instructor/workout.jsp").forward(request, response);
        }
        else if ("selectUser".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/instructor/selectUser.jsp").forward(request, response);
        }

        else if ("workoutStats".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/client/workoutStats.jsp").forward(request, response);
        }

        else if ("workoutLogs".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/client/workoutLogs.jsp").forward(request, response);
        }

        else if ("clientSession".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
        }

        else if ("upcomingSessions".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/instructor/upcomingSessions.jsp").forward(request, response);
        }

        else if ("clientWorkout".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/client/clientWorkout.jsp").forward(request, response);
        }

        else if ("terms".equals(page)) {
            // Forward to the dropdown UI JSP page
            request.getRequestDispatcher("/WEB-INF/views/owner/termsAndConditions.jsp").forward(request, response);
        }

        else {
            // Default to the workout options JSP page
            request.getRequestDispatcher("/WEB-INF/views/instructor/createAndUpdateWorkout.jsp").forward(request, response);
        }
    }
}
