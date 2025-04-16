package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.model.User;
import org.example.demo2.util.HashUtil;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addInstructor")
public class OwnerAddInstructorServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/owner/addInstructor.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("tempPassword");
        HttpSession session = request.getSession();
        session.setAttribute("userEmail", email);

        // Hash the password
        String hashedPassword = HashUtil.hashPassword(password);

        try {
            InstructorOnBoardingDAO instructorDAO = new InstructorOnBoardingDAO();

            User user = new User(firstName, lastName, email, hashedPassword, "instructor");

            boolean success = instructorDAO.addInstructor(user);

            if (success) {
                request.setAttribute("Success", "Instructor added successfully");
                response.sendRedirect(request.getContextPath() + "/instructorList?success=true");
            } else {
                request.setAttribute("error", "Failed to add instructor");
                request.getRequestDispatcher("/WEB-INF/views/owner/addInstructor.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/addInstructor.jsp").forward(request, response);
        }
    }
}
