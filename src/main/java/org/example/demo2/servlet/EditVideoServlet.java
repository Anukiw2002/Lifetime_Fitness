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

@WebServlet("/editVideo")
public class EditVideoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int videoId = Integer.parseInt(request.getParameter("videoId"));
        String videoTitle = request.getParameter("videoTitle");
        String videoLink = request.getParameter("videoLink");
        String videoDescription = request.getParameter("videoDescription");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
            String updateQuery = "UPDATE videos SET title = ?, link = ?, description = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setString(1, videoTitle);
                stmt.setString(2, videoLink);
                stmt.setString(3, videoDescription);
                stmt.setInt(4, videoId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    request.setAttribute("message", "Video updated successfully.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/editSuccess.jsp").forward(request, response);
                } else {
                    response.sendRedirect("editVideo.jsp?error=update_failed");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error updating video: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int videoId = Integer.parseInt(request.getParameter("videoId"));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
            String selectQuery = "SELECT * FROM videos WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
                stmt.setInt(1, videoId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Pass the data directly as attributes
                        request.setAttribute("videoId", rs.getInt("id"));
                        request.setAttribute("videoTitle", rs.getString("title"));
                        request.setAttribute("videoLink", rs.getString("link"));
                        request.setAttribute("videoDescription", rs.getString("description"));

                        request.getRequestDispatcher("/WEB-INF/views/owner/editVideo.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("editVideo.jsp?error=not_found");
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving video: " + e.getMessage(), e);
        }
    }
}
