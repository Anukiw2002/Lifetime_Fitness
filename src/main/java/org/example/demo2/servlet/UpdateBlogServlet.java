package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.model.BlogModel;
import org.example.demo2.servlet.BlogController;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UpdateBlog")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class UpdateBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("errorMessage", "Blog ID is missing!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Blog ID!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM blogs WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        BlogModel blog = new BlogModel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getString("content"),
                                resultSet.getBytes("image") // corrected: getBytes
                        );
                        request.setAttribute("blog", blog);
                        request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Blog not found!");
                        request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        Part imagePart = request.getPart("image"); // uploaded image part (can be empty)

        if (idParam == null || idParam.isEmpty() ||
                name == null || name.isEmpty() ||
                description == null || description.isEmpty() ||
                content == null || content.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idParam);

        boolean success;

        try {
            if (imagePart != null && imagePart.getSize() > 0) {
                // New image uploaded
                try (InputStream imageStream = imagePart.getInputStream()) {
                    success = BlogController.updateBlogWithImage(id, name, description, content, imageStream);
                }
            } else {
                // No new image uploaded
                success = BlogController.updateBlog(id, name, description, content);
            }

            if (success) {
                String successMessage = "Blog updated successfully!";
                response.setContentType("text/html");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('" + successMessage + "');");
                response.getWriter().println("window.location.href = 'GetAllBlogs';");
                response.getWriter().println("</script>");
            } else {
                request.setAttribute("errorMessage", "Failed to update the blog!");
                request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
        }
    }
}
