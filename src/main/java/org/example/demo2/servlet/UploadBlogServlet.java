package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UploadBlogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the uploadBlog JSP page
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String link = request.getParameter("link");

        // Log the received data
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Link: " + link);

        // Validate the inputs
        if (name == null || name.isEmpty() || description == null || description.isEmpty() || link == null || link.isEmpty()) {
            // Log validation error
            System.err.println("Validation Error: Missing required fields.");
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
            return;
        }

        // Save blog metadata to the database
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Error: Database connection is null.");
                request.setAttribute("errorMessage", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
                return;
            }

            String sql = "INSERT INTO blogs (name, link, description) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, link);
                statement.setString(3, description);

                // Log the SQL query being executed
                System.out.println("Executing SQL Query: " + statement);

                // Execute the insert and check if the data was successfully inserted
                int rowsAffected = statement.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    // Log success
                    System.out.println("Blog successfully uploaded!");
                    // JavaScript code to show the pop-up message
                    String successMessage = "Blog uploaded successfully!";
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('" + successMessage + "');");
                    response.getWriter().println("window.location.href = 'GetAllBlogs';");
                    response.getWriter().println("</script>");
                } else {
                    // Log failure
                    System.err.println("Error: No rows were inserted into the database.");
                    request.setAttribute("errorMessage", "Failed to upload the blog. Please try again.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            // Log SQL exceptions with detailed information
            System.err.println("SQL Exception occurred:");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error saving blog metadata: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
        }
    }
}
