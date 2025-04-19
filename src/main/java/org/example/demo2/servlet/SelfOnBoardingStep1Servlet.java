package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.util.HashUtil;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/selfOnBoarding/step1")
public class SelfOnBoardingStep1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingStep1.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        HttpSession session = request.getSession(false);
        String password = request.getParameter("newPassword");
        String hashedPassword = HashUtil.hashPassword(password);

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        boolean success = instructorOnBoardingDAO.resetPassword(hashedPassword, userId);

        if (success) {
            response.sendRedirect("step2");
        }
        else {
            response.sendRedirect("step1");
        }
    }
}