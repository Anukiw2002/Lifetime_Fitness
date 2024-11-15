package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/workoutOptions") // Map this servlet to /workoutOptions
public class WorkoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        else {
            // Default to the workout options JSP page
            request.getRequestDispatcher("/WEB-INF/views/instructor/createAndUpdateWorkout.jsp").forward(request, response);
        }
    }
}
