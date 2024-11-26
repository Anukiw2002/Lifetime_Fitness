package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DeleteBlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // Redirect to login if session is invalid or user is not logged in
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("errorMessage", "Blog ID is missing!");
            request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Blog ID!");
            request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            // Fetch the blog information
            String sql = "SELECT name FROM blogs WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Pass the blog name and ID to the JSP
                    request.setAttribute("blogName", resultSet.getString("name"));
                    request.setAttribute("blogId", id);
                    request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Blog not found!");
                    request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("errorMessage", "Blog ID is missing!");
            request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idParam);

        try (Connection connection = DBConnection.getConnection()) {
            // SQL query to delete the blog
            String sql = "DELETE FROM blogs WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    // Success message and redirect
                    String successMessage = "Blog deleted successfully!";
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('" + successMessage + "');");
                    response.getWriter().println("window.location.href = 'GetAllBlogs';");
                    response.getWriter().println("</script>");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete the blog!");
                    request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/deleteBlog.jsp").forward(request, response);
        }
    }
}
