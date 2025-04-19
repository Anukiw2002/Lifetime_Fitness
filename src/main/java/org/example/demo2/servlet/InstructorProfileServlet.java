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

@WebServlet("/instructorProfile")
public class InstructorProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            response.sendRedirect("landingPage");
            return;
        }
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        try {
            InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
            Instructor instructor = instructorOnBoardingDAO.getInstructorById(userId);


            request.setAttribute("instructor", instructor);
            request.getRequestDispatcher("/WEB-INF/views/instructor/instructorProfile.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}