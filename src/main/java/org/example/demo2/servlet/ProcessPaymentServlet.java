package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/processPayment")
public class ProcessPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Prevent GET requests for payment processing
        request.getRequestDispatcher("/WEB-INF/views/client/paymentIntegration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Retrieve form data
        String cardNumber = request.getParameter("cardNumber");
        String expiryMonth = request.getParameter("expiryMonth");
        String expiryYear = request.getParameter("expiryYear");
        String securityCode = request.getParameter("securityCode");

        // Payment processing logic here (you may validate and handle payments)
        if (cardNumber != null && !cardNumber.isEmpty() &&
                expiryMonth != null && !expiryMonth.isEmpty() &&
                expiryYear != null && !expiryYear.isEmpty() &&
                securityCode != null && !securityCode.isEmpty()) {

            // Simulate a successful payment process (You can replace this with actual payment API calls)
            // After simulating the payment, generate the order details for PayHere

            // Set the PayHere parameters to trigger the payment page.
            String orderId = "ORDER" + System.currentTimeMillis(); // Generate a unique order ID
            String amount = "1000.00"; // Set the payment amount
            String returnUrl = "http://localhost:8080/paymentSuccess"; // Set the return URL after payment success
            String cancelUrl = "http://localhost:8080/paymentCancel"; // Set the URL for cancellation

            // Pass the payment information to the request (so it can be accessed on the JSP page)
            request.setAttribute("orderId", orderId);
            request.setAttribute("amount", amount);
            request.setAttribute("returnUrl", returnUrl);
            request.setAttribute("cancelUrl", cancelUrl);

            // Redirect to the PayHere payment integration JSP
            request.getRequestDispatcher("/WEB-INF/views/client/paymentIntegration.jsp").forward(request, response);

        } else {
            // Redirect to error page if validation fails
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/client/paymentError.jsp").forward(request, response);
        }
    }
}

