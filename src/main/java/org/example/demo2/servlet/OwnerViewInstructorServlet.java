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

@WebServlet("/viewInstructor")
public class OwnerViewInstructorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            response.sendRedirect("landingPage");
            return;
        }
        String instructorIdParam = request.getParameter("id");

        if (instructorIdParam != null && !instructorIdParam.isEmpty()) {
            try {
                int instructorId = Integer.parseInt(instructorIdParam);
                InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
                Instructor instructor = instructorOnBoardingDAO.getInstructorById(instructorId);

                if (instructor == null) {
                    response.sendRedirect("instructorManagement");
                    return;
                }

                request.setAttribute("instructor", instructor);
                request.getRequestDispatcher("/WEB-INF/views/owner/viewInstructor.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("instructorManagement");
                return;
            }
        } else {
            response.sendRedirect("instructorManagement");
            return;
        }
    }
}