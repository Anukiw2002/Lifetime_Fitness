package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.model.Instructor;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/selfOnBoarding/completed")
public class SelfOnBoardingCompletedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        Instructor instructor = instructorOnBoardingDAO.getExistingData(userId);
        request.setAttribute("instructor", instructor);

        request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingCompleted.jsp").forward(request, response);
    }
}
