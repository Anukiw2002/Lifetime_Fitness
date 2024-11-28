package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/InstructorMemberManagement")
public class InstructorMemberManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        req.getRequestDispatcher("/WEB-INF/views/instructor/instructorMemberManagmement.jsp").forward(req, resp);
    }
}
