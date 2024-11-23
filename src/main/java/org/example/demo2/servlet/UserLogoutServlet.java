package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/logout1")
public class UserLogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();

        }

        request.getRequestDispatcher("/landingPage").forward(request, response);
    }
}
