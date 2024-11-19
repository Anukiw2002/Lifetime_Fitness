package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/editBlog")
public class EditBlogServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String blogTitle = request.getParameter("blogTitle");
        String blogLink = request.getParameter("blogLink");
        String blogDescription = request.getParameter("blogDescription");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
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
                    response.sendRedirect("editBlog.jsp?error=update_failed");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error updating blog: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("blogId"));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
            String selectQuery = "SELECT * FROM blogs WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
                stmt.setInt(1, blogId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Pass the data directly as attributes
                        request.setAttribute("blogId", rs.getInt("id"));
                        request.setAttribute("blogTitle", rs.getString("title"));
                        request.setAttribute("blogLink", rs.getString("link"));
                        request.setAttribute("blogDescription", rs.getString("description"));

                        request.getRequestDispatcher("/WEB-INF/views/owner/editBlog.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("editBlog.jsp?error=not_found");
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving blog: " + e.getMessage(), e);
        }
    }
}
