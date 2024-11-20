package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteVideo") // Servlet mapping for the video deletion
public class DeleteVideoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the video ID from the request
        String videoId = request.getParameter("videoId");

        if (videoId != null && !videoId.trim().isEmpty()) {
            try {
                // Mocking deletion logic (Replace this with actual database deletion logic)
                System.out.println("Deleting Video with ID: " + videoId);
                // Simulate successful deletion
                request.setAttribute("message", "Video with ID " + videoId + " has been successfully deleted.");
            } catch (Exception e) {
                // Simulate an error during deletion
                request.setAttribute("message", "Error occurred while deleting video with ID: " + videoId);
            }
        } else {
            // No ID entered
            request.setAttribute("message", "No Video ID provided. Please enter a valid ID.");
        }

        // Forward to the result page (e.g., deleteResult.jsp)
        request.getRequestDispatcher("/WEB-INF/views/owner/deleteSuccess.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("deleteVideo.jsp"); // Redirect to the delete form if accessed via GET
    }
}
