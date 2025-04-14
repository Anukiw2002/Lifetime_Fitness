package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DeleteBlog")
public class DeleteBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            sendAlert(response, "Blog ID is missing!", "GetAllBlogs");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            sendAlert(response, "Invalid Blog ID!", "GetAllBlogs");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "DELETE FROM blogs WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    sendAlert(response, "Blog deleted successfully!", "GetAllBlogs");
                } else {
                    sendAlert(response, "Blog not found or could not be deleted!", "GetAllBlogs");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "Database error: " + e.getMessage(), "GetAllBlogs");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Allow deletion via POST too
    }

    private void sendAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type='text/javascript'>");
        response.getWriter().println("alert('" + message.replace("'", "\\'") + "');");
        response.getWriter().println("window.location.href = '" + redirectUrl + "';");
        response.getWriter().println("</script>");
    }
}
