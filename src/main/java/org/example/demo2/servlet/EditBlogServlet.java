package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/editBlog")
public class EditBlogServlet extends HttpServlet {

    // Utility method for database connection
    private Connection getConnection() throws Exception {
        String dbUrl = System.getenv("DB_URL");  // Use environment variables for credentials
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String blogIdParam = request.getParameter("blogId");
        String blogTitle = request.getParameter("blogTitle");
        String blogLink = request.getParameter("blogLink");
        String blogDescription = request.getParameter("blogDescription");

        if (blogIdParam == null || blogTitle == null || blogLink == null || blogDescription == null) {
            request.setAttribute("error", "Invalid input. Please ensure all fields are filled.");
            request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
            return;
        }

        try (Connection connection = getConnection()) {
            int blogId = Integer.parseInt(blogIdParam);
            String updateQuery = "UPDATE blogs SET title = ?, link = ?, description = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setString(1, blogTitle);
                stmt.setString(2, blogLink);
                stmt.setString(3, blogDescription);
                stmt.setInt(4, blogId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    request.setAttribute("message", "Blog updated successfully.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/editSuccess.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Blog update failed. Please try again.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error updating blog: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogIdParam = request.getParameter("blogId");

        if (blogIdParam == null || blogIdParam.isEmpty()) {
            request.setAttribute("error", "Blog ID is required.");
            request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
            return;
        }

        try (Connection connection = getConnection()) {
            int blogId = Integer.parseInt(blogIdParam);
            String selectQuery = "SELECT * FROM blogs WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
                stmt.setInt(1, blogId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Pass the data as attributes
                        request.setAttribute("blogId", rs.getInt("id"));
                        request.setAttribute("blogTitle", rs.getString("title"));
                        request.setAttribute("blogLink", rs.getString("link"));
                        request.setAttribute("blogDescription", rs.getString("description"));
                        request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "No blog found with the given ID.");
                        request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving blog: " + e.getMessage(), e);
        }
    }
}
