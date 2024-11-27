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
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for payment processing.");
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
            // You could integrate with a payment gateway like Stripe, PayPal, etc.

            // Add confirmation details to the request if needed
            request.setAttribute("cardNumber", cardNumber);
            request.setAttribute("expiryDate", expiryMonth + "/" + expiryYear);

            // Forward to the payment confirmation page
            request.getRequestDispatcher("/WEB-INF/views/client/paymentConfirmation.jsp").forward(request, response);
        } else {
            // Redirect to error page if validation fails
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/client/paymentError.jsp").forward(request, response);
        }
    }


}
