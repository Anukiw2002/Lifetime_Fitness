package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/UploadBlog")
public class UploadBlogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        // Retrieve form data
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String content = request.getParameter("content");

        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Content: " + content);

        // Validation
        if (name == null || name.isEmpty() ||
                description == null || description.isEmpty() ||
                content == null || content.isEmpty()) {

            System.err.println("Validation Error: Missing required fields.");
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Error: Database connection is null.");
                request.setAttribute("errorMessage", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
                return;
            }

            String sql = "INSERT INTO blogs (name, description, content) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setString(3, content);

                System.out.println("Executing SQL Query: " + statement);

                int rowsAffected = statement.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Blog successfully uploaded!");
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Blog uploaded successfully!');");
                    response.getWriter().println("window.location.href = 'GetAllBlogs';");
                    response.getWriter().println("</script>");
                } else {
                    System.err.println("Error: No rows were inserted.");
                    request.setAttribute("errorMessage", "Failed to upload the blog. Please try again.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
