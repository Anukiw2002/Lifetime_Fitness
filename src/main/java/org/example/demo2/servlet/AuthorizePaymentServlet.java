/**
 * AuthorizePaymentServlet class - requests PayPal for payment.
 * Sends the order details to PayPal and redirects user to approval URL.
 *
 * @author Nam
 * @copyright https://codeJava.net
 */
package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import org.example.demo2.dao.PaymentServicesDAO;
import org.example.demo2.model.OrderDetails;

import com.paypal.base.rest.PayPalRESTException;

@WebServlet("/AuthorizePayment")
public class AuthorizePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthorizePaymentServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read payment details from form parameters
        String product = request.getParameter("product");
        String subtotal = request.getParameter("subtotal");
        String shipping = request.getParameter("shipping");
        String tax = request.getParameter("tax");
        String total = request.getParameter("total");

        // 2. Parse strings to float and create OrderDetails object
        OrderDetails orderDetail = new OrderDetails(
                product,
                Float.parseFloat(subtotal),
                Float.parseFloat(shipping),
                Float.parseFloat(tax),
                Float.parseFloat(total)
        );

        // 3. Attempt to authorize payment and redirect to PayPal approval link
        try {
            PaymentServicesDAO paymentServices = new PaymentServicesDAO();
            String approvalLink = PaymentServicesDAO.authorizePayment(orderDetail);

            response.sendRedirect(approvalLink); // Redirect to PayPal

        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
