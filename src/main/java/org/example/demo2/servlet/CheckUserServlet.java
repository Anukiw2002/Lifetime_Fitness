package org.example.demo2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;

@WebServlet("/CheckUser")
public class CheckUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT email FROM users WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Email exists, store it in the session
                String insertQuery = "INSERT INTO approved_emails (email) VALUES (?) ON CONFLICT DO NOTHING";
                try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, email);
                    insertStmt.executeUpdate();
                }
                HttpSession session = request.getSession();
                session.setAttribute("userEmail", email);
                System.out.println("Email stored in session: " + email);

                // Respond with status and redirect URL
                out.write("{\"status\":\"exists\", \"redirectUrl\":\"/processReport1\"}");
            } else {
                // Email does not exist
                out.write("{\"status\":\"not_exists\"}");
            }
        } catch (Exception e) {
            // Handle errors
            out.write("{\"status\":\"error\"}");
            e.printStackTrace();
        }
    }
}
