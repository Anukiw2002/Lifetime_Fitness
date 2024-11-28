package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.BlogModel;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

public class GetAllBlogsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        try {
            // Fetch all blogs using the BlogController
            List<BlogModel> allBlogs = BlogController.getAllBlogs();

            // Log the retrieved blogs for debugging purposes
            System.out.println("Retrieved Blogs: " + allBlogs);

            // Set the blogs as a request attribute to be accessible in the JSP
            request.setAttribute("blogs", allBlogs);

            // Forward the request to the JSP page for display
            request.getRequestDispatcher("/WEB-INF/views/owner/viewBlogs.jsp").forward(request, response);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error while fetching blogs:");
            e.printStackTrace();

            // Set an error message to display on the JSP
            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
            request.getRequestDispatcher("/WEB-INF/views/owner/viewBlogs.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        // For this servlet, forward GET requests to doPost
        doPost(request, response);
    }
}
