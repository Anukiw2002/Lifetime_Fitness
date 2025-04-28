package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/uploadVideoSuccess")
public class UploadVideoSuccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String videoName = request.getParameter("videoName");
        String videoLink = request.getParameter("videoLink");
        String videoDescription = request.getParameter("videoDescription");


        System.out.println("Video Uploaded: " + videoName + " - " + videoLink);


        request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideoSuccess.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        response.sendRedirect("uploadVideo.jsp");
    }
}
