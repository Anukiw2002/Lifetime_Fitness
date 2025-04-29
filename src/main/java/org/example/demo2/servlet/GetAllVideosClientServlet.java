package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.VideoModel;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/GetAllVideosClient")
public class GetAllVideosClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            List<VideoModel> allVideos = VideoController.getAllVideos();


            System.out.println("Retrieved Videos for Client: " + allVideos);


            request.setAttribute("videos", allVideos);


            request.getRequestDispatcher("/WEB-INF/views/client/viewVideos.jsp").forward(request, response);
        } catch (Exception e) {

            System.err.println("Error while fetching videos for the client:");
            e.printStackTrace();


            request.setAttribute("error  rMessage", "An error occurred while fetching videos.");
            request.getRequestDispatcher("/WEB-INF/views/client/viewVideos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        doGet(request, response);
    }
}
