package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.BlogModel;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/GetAllBlogsInstructor")
public class GetAllBlogsInstructorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            return;
        }

        try {
            List<BlogModel> allBlogs = BlogController.getAllBlogs();
            System.out.println("Retrieved Blogs for Client: " + allBlogs);
            request.setAttribute("blogs", allBlogs);
            request.getRequestDispatcher("/WEB-INF/views/client/viewBlogs.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error while fetching blogs for the client:");
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
            request.getRequestDispatcher("/WEB-INF/views/client/viewBlogs.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            return;
        }

        doGet(request, response);
    }
}
