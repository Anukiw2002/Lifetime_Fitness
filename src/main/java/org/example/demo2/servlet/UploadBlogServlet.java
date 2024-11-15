package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UploadBlogServlet")
public class UploadBlogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String blogTitle = request.getParameter("blogTitle");
        String blogContent = request.getParameter("blogContent");
        String blogImage = request.getParameter("blogImage");

        // Process the data, e.g., save it to a database (assuming a method `saveBlog` exists)
        // Here, a simple success message is printed as a placeholder.
        System.out.println("Blog Uploaded: " + blogTitle + " - " + blogContent);

        // Redirect to a success page or show a success message
        response.sendRedirect("uploadSuccess.jsp"); // Create this JSP page for confirmation if needed
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("uploadBlog.jsp"); // Redirect to form if accessed via GET
    }
}
