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

        String product = request.getParameter("product");
        String subtotalStr = request.getParameter("subtotal");

        try {
            if (product == null || subtotalStr == null) {
                throw new IllegalArgumentException("Missing required payment parameters.");
            }

            float subtotal = Float.parseFloat(subtotalStr.trim());

            // Pass subtotal as both subtotal and total (since no tax/shipping)
            OrderDetails orderDetail = new OrderDetails(product, subtotal, subtotal);

            PaymentServicesDAO paymentServices = new PaymentServicesDAO();
            String approvalLink = PaymentServicesDAO.authorizePayment(orderDetail);
            response.sendRedirect(approvalLink);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);

        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);
        }
    }

}



