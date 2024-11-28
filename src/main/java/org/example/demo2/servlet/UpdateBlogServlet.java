package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.BlogModel;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UpdateBlog")
public class UpdateBlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
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
                                resultSet.getString("link")
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
            return; // If not authorized, the redirection will be handled by the utility method
        }
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String link = request.getParameter("link");


        if (idParam == null || idParam.isEmpty() || name == null || name.isEmpty()
                || description == null || description.isEmpty()  ||link == null || link.isEmpty() ) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idParam);

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE blogs SET name = ?, description = ?, link = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setString(3, link);
                statement.setInt(4, id);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Add a pop-up success message
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/updateBlog.jsp").forward(request, response);
        }
    }
}
