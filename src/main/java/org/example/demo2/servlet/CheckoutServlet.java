package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String planId = request.getParameter("planId");
        String durationId = request.getParameter("durationId");

        // You can fetch plan/duration data from DB here and set attributes
        request.setAttribute("planId", planId);
        request.setAttribute("durationId", durationId);
        System.out.println("duration ID :" + durationId);
        System.out.println("plan ID : " + planId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/client/checkout.jsp");
        dispatcher.forward(request, response);
    }
}
