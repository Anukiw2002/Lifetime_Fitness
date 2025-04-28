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

@WebServlet("/GetAllVideos")
public class GetAllVideosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        try {

            List<VideoModel> allVideos = VideoController.getAllVideos();


            System.out.println("Retrieved Videos: " + allVideos);


            request.setAttribute("videos", allVideos);


            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        } catch (Exception e) {

            System.err.println("Error while fetching videos:");
            e.printStackTrace();


            request.setAttribute("errorMessage", "An error occurred while fetching videos.");
            request.getRequestDispatcher("/WEB-INF/views/owner/viewVideos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        doPost(request, response);
    }
}
