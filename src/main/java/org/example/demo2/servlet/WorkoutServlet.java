package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/workoutOptionss")
public class WorkoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String page = request.getParameter("page");

        if ("newWorkout".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/instructor/newWorkout.jsp").forward(request, response);
        }
        else if ("workout".equals(page)){
            request.getRequestDispatcher("/WEB-INF/views/instructor/workout.jsp").forward(request, response);
        }
        else if ("selectUser".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/instructor/selectUser.jsp").forward(request, response);
        }

        else if ("workoutStats".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/client/workoutStats.jsp").forward(request, response);
        }

        else if ("workoutLogs".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/client/workoutLogs.jsp").forward(request, response);
        }

        else if ("clientSession".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/client/clientSession.jsp").forward(request, response);
        }

        else if ("upcomingSessions".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/instructor/upcomingSessions.jsp").forward(request, response);
        }

        else if ("clientWorkout".equals(page)) {

            response.sendRedirect(request.getContextPath() + "/clientWorkoutView");
            return;
        }

        else if ("terms".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/owner/termsAndConditions.jsp").forward(request, response);
        }

        else if ("clientWorkoutDetails".equals(page)) {

            request.getRequestDispatcher("/WEB-INF/views/client/clientWorkout-details.jsp").forward(request, response);
        }

        else {

            request.getRequestDispatcher("/WEB-INF/views/instructor/createAndUpdateWorkout.jsp").forward(request, response);
        }
    }
}
