package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/paymentCancelled")
public class PaymentCancelServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the order ID (if passed) or any cancellation info
        String orderId = request.getParameter("order_id");

        // Handle cancellation logic (like notifying the user, logging, etc.)
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2 style='color: red; text-align: center;'>Payment Cancelled. Order ID: " + orderId + "</h2>");
    }
}
