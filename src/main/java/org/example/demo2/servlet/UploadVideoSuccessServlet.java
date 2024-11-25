package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/uploadVideoSuccess")
public class UploadVideoSuccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String videoName = request.getParameter("videoName");
        String videoLink = request.getParameter("videoLink");
        String videoDescription = request.getParameter("videoDescription");

        // Process the data, e.g., save it to a database (assuming a method `saveVideo` exists)
        // Here, a simple success message is printed as a placeholder.
        System.out.println("Video Uploaded: " + videoName + " - " + videoLink);

        // Redirect to a success page or show a success message
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideoSuccess.jsp").forward(request, response); // Create this JSP page for confirmation if needed
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("uploadVideo.jsp"); // Redirect to form if accessed via GET
    }
}
