package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.VideoModel;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

// Define the servlet mapping

public class GetAllVideosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for a valid session
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }

        try {
            // Fetch all videos using the VideoController
            List<VideoModel> allVideos = VideoController.getAllVideos();

            // Log retrieved videos for debugging purposes
            System.out.println("Retrieved Videos: " + allVideos);

            // Set the videos as a request attribute for the JSP
            request.setAttribute("videos", allVideos);

            // Forward the request to the JSP page for display
            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error while fetching videos:");
            e.printStackTrace();

            // Set an error message for the JSP
            request.setAttribute("errorMessage", "An error occurred while fetching videos.");
            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        // Forward GET requests to doPost
        doPost(request, response);
    }
}
