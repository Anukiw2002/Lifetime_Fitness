package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteBlog")
public class DeleteBlogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the blog ID from the request
        String blogId = request.getParameter("blogId");

        if (blogId != null && !blogId.trim().isEmpty()) {
            try {
                // Mocking deletion logic (Replace this with actual database deletion logic)
                System.out.println("Deleting Blog with ID: " + blogId);
                // Simulate successful deletion
                request.setAttribute("message", "Blog with ID " + blogId + " has been successfully deleted.");
            } catch (Exception e) {
                // Simulate an error during deletion
                request.setAttribute("message", "Error occurred while deleting blog with ID: " + blogId);
            }
        } else {
            // No ID entered
            request.setAttribute("message", "No Blog ID provided. Please enter a valid ID.");
        }

        // Forward to the result page (e.g., deleteResult.jsp)
        request.getRequestDispatcher("/WEB-INF/views/owner/deleteSuccess.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("deleteBlog.jsp"); // Redirect to the delete form if accessed via GET
    }
}
