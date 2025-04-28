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

@WebServlet("/GetAllBlogs")
public class GetAllBlogsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        try {

            List<BlogModel> allBlogs = BlogController.getAllBlogs();


            System.out.println("Retrieved Blogs: " + allBlogs);


            request.setAttribute("blogs", allBlogs);


            request.getRequestDispatcher("/WEB-INF/views/owner/viewBlogs.jsp").forward(request, response);
        } catch (Exception e) {

            System.err.println("Error while fetching blogs:");
            e.printStackTrace();


            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
            request.getRequestDispatcher("/WEB-INF/views/owner/viewBlogs.jsp").forward(request, response);
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
