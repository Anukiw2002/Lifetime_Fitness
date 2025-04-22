package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.IUserDAO;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.dao.UserDAOImpl;
import org.example.demo2.model.User;
import org.example.demo2.util.HashUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet("/signup")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        session.setAttribute("userEmail", email);

        // Validate inputs
        if (isInvalidInput(firstName, lastName, email, password, confirmPassword)) {
            sendAlert(response, "All fields are required, passwords must match, and email must be valid.", "testView?page=page1");
            return;
        }

        // Hash the password
        String hashedPassword = HashUtil.hashPassword(password);

        try {
            UserDAO userDAO = new UserDAO();

            // Check for existing username or email
            if (userDAO.emailExists(email)) {
                sendAlert(response, "Email already exists.", "testView?page=page1");
                return;
            }

            // Create the user with role set to "client"
            User user = new User(firstName, lastName, email, hashedPassword, "client");
            userDAO.registerUser(user);
            session.setAttribute("userId", user.getUser_id());

            // Redirect to login page after successful registration
            sendAlert(response, "Registration successful. ", "/signup/step2");
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "An error occurred while processing your registration. Please try again later.", "testView?page=page1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    // Helper method to validate inputs
    private boolean isInvalidInput(String firstName, String lastName, String email, String password, String confirmPassword) {
        return firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || email == null || email.isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()
                || !password.equals(confirmPassword)
                || password.length() < 8
                || !isValidEmail(email);
    }

    // Validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Helper method to send JavaScript alerts and redirect
    private void sendAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + escapeJavaScript(message) + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL(redirectUrl) + "';");
        response.getWriter().println("</script>");
    }

    // Escape JavaScript strings to prevent injection
    private String escapeJavaScript(String message) {
        return message.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }
}