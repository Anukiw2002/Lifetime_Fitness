package org.example.demo2.servlet;

import org.example.demo2.dao.UserDAOImpl;
import org.example.demo2.dao.IUserDAO;
import org.example.demo2.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Email regex for basic validation
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        // Server-side validation
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            sendAlert(response, "Email and password are required.");
            return;
        }

        if (!email.matches(emailRegex)) {
            sendAlert(response, "Invalid email format.");
            return;
        }

        if (password.length() < 8) {
            sendAlert(response, "Password must be at least 8 characters long.");
            return;
        }

        IUserDAO userDAO = new UserDAOImpl();
        User user = null;

        try {
            // Attempt to retrieve the user from the database
            user = userDAO.getUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "Database error occurred while retrieving user.");
            return;
        }

        // Check if user exists and password is correct
        if (user != null) {
            boolean passwordMatch = userDAO.verifyPassword(password, user.getHashedPassword());
            if (passwordMatch) {
                // Create a session and redirect to dashboard
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            } else {
                sendAlert(response, "Invalid password.");
            }
        } else {
            sendAlert(response, "No user found with the provided email.");
        }
    }

    // Helper function to send a JavaScript alert
    private void sendAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + message + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL("login.jsp") + "';");
        response.getWriter().println("</script>");
    }
}
