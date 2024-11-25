package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.BlogModel;

import java.io.IOException;
import java.util.List;


public class GetAllBlogsClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch all blogs using the BlogController
            List<BlogModel> allBlogs = BlogController.getAllBlogs();

            // Log the retrieved blogs for debugging purposes
            System.out.println("Retrieved Blogs for Client: " + allBlogs);

            // Set the blogs as a request attribute to be accessible in the JSP
            request.setAttribute("blogs", allBlogs);

            // Forward the request to the JSP page for display
            request.getRequestDispatcher("/WEB-INF/views/client/viewBlogs.jsp").forward(request, response);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error while fetching blogs for the client:");
            e.printStackTrace();

            // Set an error message to display on the JSP
            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
            request.getRequestDispatcher("/WEB-INF/views/client/viewBlogs.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For this servlet, forward POST requests to doGet
        doGet(request, response);
    }
}
