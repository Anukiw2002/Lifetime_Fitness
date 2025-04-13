package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/UploadVideo")
public class UploadVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String videoName = request.getParameter("videoName");
        String videoDescription = request.getParameter("videoDescription");
        String videoUrl = request.getParameter("videoUrl");

        if (videoName == null || videoName.isEmpty() ||
                videoDescription == null || videoDescription.isEmpty() ||
                videoUrl == null || videoUrl.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                request.setAttribute("errorMessage", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
                return;
            }

            String sql = "INSERT INTO videos (name, description, url) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, videoName);
                statement.setString(2, videoDescription);
                statement.setString(3, videoUrl);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Video link uploaded successfully!');");
                    response.getWriter().println("window.location.href = 'GetAllVideos';");
                    response.getWriter().println("</script>");
                } else {
                    request.setAttribute("errorMessage", "Failed to upload the video.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error saving video metadata: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
        }
    }
}
