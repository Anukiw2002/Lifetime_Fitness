package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.model.VideoModel;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.*;

@WebServlet("/UpdateVideo")
public class UpdateVideoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("errorMessage", "Video ID is missing!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Video ID!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM videos WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        VideoModel video = new VideoModel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getString("url")
                        );
                        request.setAttribute("video", video);
                        request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Video not found!");
                        request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String idParam = request.getParameter("id");
        String name = request.getParameter("videoName");
        String description = request.getParameter("videoDescription");
        String url = request.getParameter("videoUrl");

        if (idParam == null || idParam.isEmpty() ||
                name == null || name.isEmpty() ||
                description == null || description.isEmpty() ||
                url == null || url.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idParam);

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE videos SET name = ?, description = ?, url = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setString(3, url);
                statement.setInt(4, id);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Video updated successfully!');");
                    response.getWriter().println("window.location.href = 'GetAllVideos';");
                    response.getWriter().println("</script>");
                } else {
                    request.setAttribute("errorMessage", "Failed to update the video!");
                    request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/updateVideo.jsp").forward(request, response);
        }
    }
}
