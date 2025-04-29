package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.util.SessionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@WebServlet("/UploadBlog")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class UploadBlogServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        request.setCharacterEncoding("UTF-8");


        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        Part imagePart = request.getPart("image");


        if (name == null || description == null || content == null || imagePart == null || imagePart.getSize() == 0) {
            request.setAttribute("errorMessage", "All fields including image are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
            return;
        }


        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();


        String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
        String image = UPLOAD_DIR + "/" + fileName;
        File file = new File(uploadPath + File.separator + fileName);


        try (InputStream input = imagePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }

        try (InputStream imageStream = imagePart.getInputStream()) {
            boolean success = BlogController.insertBlogWithImage(name, description, content, imageStream);
            if (success) {
                response.sendRedirect("GetAllBlogs");
            } else {
                request.setAttribute("errorMessage", "Blog upload failed. Try again.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadBlog.jsp").forward(request, response);
        }
    }
}
