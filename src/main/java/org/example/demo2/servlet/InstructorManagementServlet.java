package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.model.Instructor;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/instructorManagement")
public class InstructorManagementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            response.sendRedirect("landingPage");
        }

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        List<Instructor> instructors = instructorOnBoardingDAO.getAllInstructors();
        request.setAttribute("instructors", instructors);

        request.getRequestDispatcher("/WEB-INF/views/owner/instructorManagement.jsp").forward(request,response);

    }

}
