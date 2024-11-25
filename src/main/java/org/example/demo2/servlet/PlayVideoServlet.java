package org.example.demo2.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class PlayVideoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the JSP
        request.getRequestDispatcher("/WEB-INF/views/playVideo.jsp").forward(request, response);
    }
}
