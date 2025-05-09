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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        session.setAttribute("userEmail", email);

        if (isInvalidInput(firstName, lastName, email, password, confirmPassword)) {
            sendAlert(response, "All fields are required, passwords must match, and email must be valid.", "testView?page=page1");
            return;
        }

        String hashedPassword = HashUtil.hashPassword(password);

        try {
            UserDAO userDAO = new UserDAO();

            if (userDAO.emailExists(email)) {
                sendAlert(response, "Email already exists.", "testView?page=page1");
                return;
            }

            User user = new User(firstName, lastName, email, hashedPassword, "client");
            userDAO.registerUser(user);
            session.setAttribute("userId", user.getUser_id());

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

    private boolean isInvalidInput(String firstName, String lastName, String email, String password, String confirmPassword) {
        return firstName == null || firstName.isEmpty() || !firstName.matches("[A-Za-z .'-]+")
                || lastName == null || lastName.isEmpty() || !lastName.matches("[A-Za-z .'-]+")
                || email == null || email.isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()
                || !password.equals(confirmPassword)
                || password.length() < 8
                || !isValidEmail(email);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private void sendAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + escapeJavaScript(message) + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL(redirectUrl) + "';");
        response.getWriter().println("</script>");
    }

    private String escapeJavaScript(String message) {
        return message.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }
}