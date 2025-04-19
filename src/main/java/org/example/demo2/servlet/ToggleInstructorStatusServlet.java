package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/toggleInstructorStatus")
public class ToggleInstructorStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            response.sendRedirect("landingPage");
            return;
        }

        String instructorIdParam = request.getParameter("id");

        if (instructorIdParam != null && !instructorIdParam.isEmpty()) {
            try {
                int instructorId = Integer.parseInt(instructorIdParam);
                InstructorOnBoardingDAO dao = new InstructorOnBoardingDAO();

                boolean success = dao.changeIsActiveStatus(instructorId);

                if (success) {
                    response.sendRedirect("editInstructor?id=" + instructorId);
                } else {
                    response.sendRedirect("viewInstructor");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("viewInstructor");
            }
        } else {
            response.sendRedirect("viewInstructor");
        }
    }
}