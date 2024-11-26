package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.util.DBConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/uploadVideo")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class UploadVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "videos";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String videoName = request.getParameter("videoName");
        String videoDescription = request.getParameter("videoDescription");
        Part videoFile = request.getPart("videoFile");

        if (videoName == null || videoName.isEmpty() || videoDescription == null || videoDescription.isEmpty() || videoFile == null) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            request.setAttribute("errorMessage", "Failed to create upload directory.");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        String fileName = videoFile.getSubmittedFileName();
        String filePath = uploadPath + File.separator + fileName;

        try {
            videoFile.write(filePath);
        } catch (IOException e) {
            request.setAttribute("errorMessage", "Failed to save the video file.");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                request.setAttribute("errorMessage", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
                return;
            }

            String sql = "INSERT INTO videos (name, description) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, videoName);
                statement.setString(2, videoDescription);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    String successMessage = "Video uploaded successfully!";
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('" + successMessage + "');");
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
