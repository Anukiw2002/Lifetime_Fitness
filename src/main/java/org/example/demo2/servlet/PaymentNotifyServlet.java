package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/paymentNotify")
public class PaymentNotifyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the notification data from PayHere
        String orderId = request.getParameter("order_id");
        String paymentStatus = request.getParameter("payment_status");

        // Handle the payment notification (you might want to update your database, etc.)
        // For now, just log the notification
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Received Payment Notification:</h2>");
        out.println("<p>Order ID: " + orderId + "</p>");
        out.println("<p>Payment Status: " + paymentStatus + "</p>");
    }
}
