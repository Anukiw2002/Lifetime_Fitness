package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.demo2.util.DBConnection;

@WebServlet("/viewEachBlog")
public class ViewEachBlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendRedirect("GetAllBlogs");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM blogs WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(id));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    request.setAttribute("name", rs.getString("name"));
                    request.setAttribute("description", rs.getString("description"));
                    request.setAttribute("content", rs.getString("content"));
                    request.getRequestDispatcher("/WEB-INF/views/owner/viewEachBlog.jsp").forward(request, response);
                } else {
                    response.sendRedirect("GetAllBlogs");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("GetAllBlogs");
        }
    }
}
