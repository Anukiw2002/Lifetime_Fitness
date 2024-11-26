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
import org.apache.commons.text.StringEscapeUtils;

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
                // Create a session and redirect based on the user's role
                HttpSession session = request.getSession();
                session.setAttribute("userRole", user.getRole());
                session.setMaxInactiveInterval(30*60);;

                // Role-based redirection
                switch (user.getRole()) {
                    case "client":
                        request.getRequestDispatcher("/WEB-INF/views/client/memberProfile.jsp").forward(request, response);
                        break;
                    case "owner":
                        request.getRequestDispatcher("/WEB-INF/views/owner/memberManagement.jsp").forward(request, response);
                        break;
                    case "instructor":
                        request.getRequestDispatcher("/upcomingSessions").forward(request, response);
                        break;
                    default:
                        sendAlert(response, "Unknown user role.");
                        break;
                }

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
        String sanitizedMessage = StringEscapeUtils.escapeJson(message);
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + sanitizedMessage + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL("testView?page=login") + "';");
        response.getWriter().println("</script>");
    }
}