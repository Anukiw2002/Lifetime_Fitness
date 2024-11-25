package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.VideoModel;

import java.io.IOException;
import java.util.List;


public class GetAllVideosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch all videos using the VideoController
            List<VideoModel> allVideos = VideoController.getAllVideos();

            // Log the retrieved videos for debugging purposes
            System.out.println("Retrieved Videos: " + allVideos);

            // Set the videos as a request attribute to be accessible in the JSP
            request.setAttribute("videos", allVideos);

            // Forward the request to the JSP page for display
            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error while fetching videos:");
            e.printStackTrace();

            // Set an error message to display on the JSP
            request.setAttribute("errorMessage", "An error occurred while fetching videos.");
            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For this servlet, forward GET requests to doPost
        doPost(request, response);
    }
}