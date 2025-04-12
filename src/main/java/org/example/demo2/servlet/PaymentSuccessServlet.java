package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/paymentSuccess")
public class PaymentSuccessServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the order ID from the request (if passed by PayHere)
        String orderId = request.getParameter("order_id");

        // Handle success logic (like updating database, showing confirmation, etc.)
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2 style='color: green; text-align: center;'>Payment Successful! Order ID: " + orderId + "</h2>");
    }
}
