package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/contentManagement")
public class ContentManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the action and type parameters from the request
        String action = request.getParameter("action");
        String type = request.getParameter("type");

        // Log action and type for debugging purposes
        System.out.println("Action: " + action + ", Type: " + type);

        // Determine the redirect URL based on the action and type
        String redirectURL = getRedirectURL(action, type);

        // Redirect to the relevant page or default to the content management page
        if (redirectURL != null) {
            response.sendRedirect(request.getContextPath() + redirectURL);
        } else {
            response.sendRedirect(request.getContextPath() + "/contentManagement.jsp");
        }
    }

    private static String getRedirectURL(String action, String type) {
        if ("upload".equalsIgnoreCase(action)) {
            if ("video".equalsIgnoreCase(type)) {
                return "/WEB-INF/views/owner/uploadVideo.jsp";
            } else if ("blog".equalsIgnoreCase(type)) {
                return "/WEB-INF/views/owner/uploadBlog.jsp";
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            if ("video".equalsIgnoreCase(type)) {
                return "/WEB-INF/views/owner/editVideo.jsp";
            } else if ("blog".equalsIgnoreCase(type)) {
                return "/WEB-INF/views/owner/editBlog.jsp";
            }
        }
        return null; // Default if no matching action and type
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect GET requests to contentManagement.jsp
        response.sendRedirect(request.getContextPath() + "/contentManagement.jsp");
    }
}
