package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.servlet.VideoController;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/UploadVideo")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // 10MB max file size
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

        // Form fields
        String videoName = request.getParameter("videoName");
        String videoDescription = request.getParameter("videoDescription");
        String videoUrl = request.getParameter("videoUrl");
        Part imagePart = request.getPart("videoImage"); // "videoImage" is the name of your <input type="file" />

        if (videoName == null || videoName.isEmpty() ||
                videoDescription == null || videoDescription.isEmpty() ||
                videoUrl == null || videoUrl.isEmpty() ||
                imagePart == null || imagePart.getSize() == 0) {

            request.setAttribute("errorMessage", "All fields including image are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        InputStream imageInputStream = imagePart.getInputStream();

        // Insert using VideoController
        boolean success = VideoController.insertVideoWithImage(videoName, videoDescription, videoUrl, imageInputStream);

        if (success) {
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Video uploaded successfully!');");
            response.getWriter().println("window.location.href = 'GetAllVideos';");
            response.getWriter().println("</script>");
        } else {
            request.setAttribute("errorMessage", "Failed to upload the video.");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
        }
    }
}
