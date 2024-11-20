package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/uploadBlog")
public class UploadBlogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String blogTitle = request.getParameter("blogTitle");
        String blogContent = request.getParameter("blogContent");
        String blogImage = request.getParameter("blogImage");

        // Log the uploaded blog details (for debugging purposes)
        System.out.println("Blog Uploaded: " + blogTitle + " - " + blogContent);

        // Forward the request to the uploadSuccess.jsp page
        request.setAttribute("blogTitle", blogTitle); // Pass data if needed
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadSuccess.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("uploadBlog.jsp"); // Redirect to form if accessed via GET
    }
}
